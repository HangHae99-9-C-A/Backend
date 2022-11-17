package com.example.soldapple.personal;

import com.example.soldapple.aws_s3.S3UploadUtil;
import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.repository.IssuesRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.post.dto.CommentResponseDto;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.post.service.PostService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true) // default = false, but intuitive
@Service
public class PersonalService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final IssuesRepository issuesRepository;
    private final PostService postService;
    private final S3UploadUtil s3UploadUtil;

    //내 페이지 불러오기

    public PersonalResponseDto getMyPage(Member member) {
        //회원 여부 체크
        memberCheck(member);

        //get my (Post Comment Likes Issues) Lists

        // List<Post> postList = postRepository.findAllByMember(member);
        // List<Comment> commentList = commentRepository.findAllByMember(member);
        //List<Likes> myLikesList = postRepository.findAllByMember(member);
        List<Issues> issuesList = issuesRepository.findAllByMember(member);

        //response builder with Dto
        PersonalResponseDto personalResponseDto = PersonalResponseDto.builder()
                //   .myPostList(postList.stream().map(PostResponseDto::new).collect(Collectors.toList()))
                //  .myCommentList(commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList()))
                // .myLikesList(myLikesList.stream().map(LikesResponseDto::new).collect(Collectors.toList()))
                .myIssuesList(issuesList.stream().map(IssuesResponseDto::new).collect(Collectors.toList()))
                .build();

        return personalResponseDto;

    }

    //내 페이지 수정하기
    @Transactional(readOnly = false)
    public void editMyPage(MyInfoRequestDto myInfoRequestDto, Member member, MultipartFile imgFile) throws IOException {
        Member editMyMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);
        if (!(imgFile == null)) {
            var r = s3UploadUtil.upload(imgFile, "test");
            editMyMember.update(myInfoRequestDto, r);
        } else {
            editMyMember.update(myInfoRequestDto);
//            return new PostResponseDto(post);
        }

        //memberResponse 필요합니다. 다른분 개발 부탁

    }

    public PersonalResponseDto getMyPost(Member member) {
        //회원 여부 체크
        memberCheck(member);
        List<Post> postList = postRepository.findAllByMember(member);
        PersonalResponseDto personalResponseDto = PersonalResponseDto.builder()
                .myPostList(postList.stream().map(PostResponseDto::new).collect(Collectors.toList()))
                .build();
        return personalResponseDto;
    }

    public PersonalResponseDto getMyComment(Member member) {
        //회원 여부 체크
        memberCheck(member);
        List<Comment> commentList = commentRepository.findAllByMember(member);
        PersonalResponseDto personalResponseDto = PersonalResponseDto.builder()
                .myCommentList(commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList()))
                .build();
        return personalResponseDto;

    }

    public PersonalResponseDto getMyIssue(Member member) {
        //회원 여부 체크
        memberCheck(member);
        List<Issues> issuesList = issuesRepository.findAllByMember(member);
        PersonalResponseDto personalResponseDto = PersonalResponseDto.builder()
                .myIssuesList(issuesList.stream().map(IssuesResponseDto::new).collect(Collectors.toList()))
                .build();
        return personalResponseDto;
    }

    //내 likes한 post 가져오기
    public PersonalResponseDto getMyLikes(Member member) {
        Member myMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);
        List<Post> myLikesList = postRepository.findAllMyTest(member);
        PersonalResponseDto personalResponseDto = PersonalResponseDto.builder()
                .myLikesList(myLikesList.stream().map(PostResponseDto::new).collect(Collectors.toList()))
                .build();
        return personalResponseDto;
    }

    // 마이페이지 정보 불러오기
    public MemberResponseDto getMyInfo(Member member) {
        //회원 여부 체크
        memberCheck(member);
        return new MemberResponseDto(member);

    }

    //판매자 정보페이지 불러오기 api 수정하였습니다
    public PersonalResponseDto getUserInfo(Long memberId) {
        Member sellerMember = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
        List<Post> postList = postRepository.findAllByMember(sellerMember);
        PersonalResponseDto personalResponseDto = PersonalResponseDto.builder()
                .myPostList(postList.stream().map(PostResponseDto::new).collect(Collectors.toList()))
                .sellerInfoDto(new MemberResponseDto(sellerMember))
                .build();
        return personalResponseDto;
    }





    //Response
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Data
    static class PersonalResponseDto {
        private List<PostResponseDto> myPostList;
        private List<CommentResponseDto> myCommentList;
        private List<PostResponseDto> myLikesList;
        private List<IssuesResponseDto> myIssuesList;
        private MemberResponseDto sellerInfoDto;

    }

    //Methods
    public void memberCheck(Member member) {
        memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);
    }

    public void memberNicknameCheck(String nickname) {

    }
}
