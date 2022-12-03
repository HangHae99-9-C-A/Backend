package com.example.soldapple.issues.service;

import com.example.soldapple.aws_s3.S3UploadUtil;
import com.example.soldapple.create_price.dto.GetIPhonePriceResDto;
import com.example.soldapple.create_price.dto.GetMacbookPriceResDto;
import com.example.soldapple.error.CustomException;
import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesCommentResponseDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.IssuesComment;
import com.example.soldapple.issues.entity.IssuesImage;
import com.example.soldapple.issues.entity.IssuesOpt;
import com.example.soldapple.issues.repository.IssuesCommentRepository;
import com.example.soldapple.issues.repository.IssuesImageRepository;
import com.example.soldapple.issues.repository.IssuesOptRepository;
import com.example.soldapple.issues.repository.IssuesRepository;
import com.example.soldapple.like.repository.IssuesLikeRepository;
import com.example.soldapple.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.soldapple.error.ErrorCode.CANNOT_DELETE_NOT_EXIST_POST;
import static com.example.soldapple.error.ErrorCode.CANNOT_FIND_POST_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class IssuesService {
    private final IssuesRepository issuesRepository;
    private final S3UploadUtil s3UploadUtil;
    private final IssuesImageRepository issuesimageRepository;
    private final IssuesLikeRepository issuesLikeRepository;
    private final IssuesOptRepository issuesOptRepository;
    private final IssuesCommentRepository issuesCommentRepository;

    //이의제기글 작성
    public IssuesResponseDto createIssue(List<MultipartFile> multipartFiles,
                                         IssuesRequestDto issuesRequestDto,
                                         GetIPhonePriceResDto iphoneOption,
                                         GetMacbookPriceResDto macbookOption,
                                         Member member) throws IOException{
        Issues issues = new Issues(issuesRequestDto, member);
        issuesRepository.save(issues);
        Boolean myIssue;
        myIssue = issues.getMember().getId().equals(member.getId());

        //이미지 저장
        List<IssuesImage> imageList = new ArrayList<>();
        if(!(multipartFiles.size()==0)){
            System.out.println(multipartFiles.get(0).getOriginalFilename());
            for(MultipartFile imgFile : multipartFiles){
                Map<String, String> img = s3UploadUtil.upload(imgFile, "issue-img");
                IssuesImage issuesImage = new IssuesImage(img, issues);
                imageList.add(issuesImage);
                issuesimageRepository.save(issuesImage);
            }
        }

        issues.setIssuesImages(imageList);

        /*옵션항목들 저장*/
        if (iphoneOption==null){
            //맥북일 때
            IssuesOpt options = new IssuesOpt(macbookOption, issues);
            issuesOptRepository.save(options);
            issues.setIssuesOpt(options);
        } else{
            //아이폰일 때
            IssuesOpt options = new IssuesOpt(iphoneOption, issues);
            issuesOptRepository.save(options);
            issues.setIssuesOpt(options);
        }

        String avatarUrl = checkAvatar(issues);
        Boolean isLike = issuesLikeRepository.existsByIssuesAndMember(issues, member);
        return new IssuesResponseDto(issues, isLike, avatarUrl, commentDtos(issues, member.getId()), myIssue);

    }


    //이의제기글 수정
    public IssuesResponseDto updateIssue(Long issuesId,IssuesRequestDto issuesRequestDto, Member member) {
        Issues issues = issuesRepository.findByIssuesIdAndMember(issuesId, member).orElseThrow(
                ()->new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        Boolean myIssue;
        myIssue = issues.getMember().getId().equals(member.getId());
        issues.update(issuesRequestDto);
        issuesRepository.save(issues);
        Boolean isLike = issuesLikeRepository.existsByIssuesAndMember(issues,member);
        String avatarUrl = checkAvatar(issues);

        return new IssuesResponseDto(issues,isLike,avatarUrl, commentDtos(issues, member.getId()), myIssue);
    }

    //이의제기글 삭제
    public String deleteIssue(Long issuesId, Member member) {
        Issues issues = issuesRepository.findByIssuesIdAndMember(issuesId, member).orElseThrow(
                ()->new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        List<IssuesImage> imageList = issues.getIssuesImages();
        for (IssuesImage issuesImage : imageList) {
            s3UploadUtil.delete(issuesImage.getImgKey());
        }
        issuesRepository.delete(issues);
        return "이의제기글 삭제 완료";
    }

    //이의제기글 하나 조회
    public IssuesResponseDto oneIssue(Long issuesId, Member member) {
        Issues issues = issuesRepository.findByIssuesId(issuesId).orElseThrow(
                () -> new CustomException(CANNOT_DELETE_NOT_EXIST_POST)
        );
        Boolean myIssue;
        myIssue = issues.getMember().getId().equals(member.getId());
        Boolean isLike = issuesLikeRepository.existsByIssuesAndMember(issues, member);
        String avatarUrl = checkAvatar(issues);

        return new IssuesResponseDto(issues, isLike, avatarUrl, commentDtos(issues, member.getId()), myIssue);
    }

    //댓글목록 dto 넣기
    private List<IssuesCommentResponseDto> commentDtos(Issues issues, Long memberId) {
        List<IssuesComment> issuesComments = issues.getIssuesComments();
        List<IssuesCommentResponseDto> issuesCommentResponseDtos = new ArrayList<>();
        String avatarUrl;
        Boolean myComment;
        if(!(issuesComments==null)) {
            for (IssuesComment issuesComment : issuesComments) {
                myComment = issuesComment.getMember().getId().equals(memberId);
                if(issuesComment.getMember().getAvatarUrl()==null) {
                    avatarUrl = "https://querybuckets.s3.ap-northeast-2.amazonaws.com/default/photoimg.png";
                } else{
                    avatarUrl = issuesComment.getMember().getAvatarUrl();
                }
                issuesCommentResponseDtos.add(new IssuesCommentResponseDto(issuesComment, avatarUrl, myComment));
            }
        }
        return issuesCommentResponseDtos;
    }

    //프로필사진 있는지 확인
    private String checkAvatar(Issues issues) {
        if (issues.getMember().getAvatarUrl()==null) {
            return "https://querybuckets.s3.ap-northeast-2.amazonaws.com/default/photoimg.png";
        }
        else{
            return issues.getMember().getAvatarUrl();
        }
    }

    //이의글 전체 조회 무한스크롤
    public Page<IssuesResponseDto> getAllIssues(Pageable pageable) {
        return issuesRepository.findAllMyIssues(pageable);
    }
    //이의글 전체 조회 +검색 무한스크롤
    public Page<IssuesResponseDto> getAllIssuesWithSearch(Pageable pageable,String search) {
        return issuesRepository.findAllMyIssuesWithSearch(pageable,search);
    }
    //category + 내 좋아요 무한스크롤
    public Page<IssuesResponseDto> getAllIssuesWithCategory(Pageable pageable, String category) {
        Page<IssuesResponseDto> allPostWithCategory = issuesRepository.findAllIssuesWithCategory(pageable, category);
        return allPostWithCategory;
    }
    //category + 검색 + 정렬
    public Page<?> getAllIssuesWithCategoryWithSearch(Pageable pageable, String category,String search) {
        Page<?> allIssuesWithCategoryWithSearch = issuesRepository.findAllIssuesWithCategoryWithSearch(pageable, category,search);
        return allIssuesWithCategoryWithSearch;
    }

}