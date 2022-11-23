package com.example.soldapple.post.service;


import com.example.soldapple.aws_s3.S3UploadUtil;
import com.example.soldapple.create_price.dto.GetIPhonePriceResDto;
import com.example.soldapple.create_price.dto.GetMacbookPriceResDto;
import com.example.soldapple.like.repository.LikeRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.CommentResponseDto;
import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.post.entity.Image;
import com.example.soldapple.post.entity.Opt;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.repository.ImageRepository;
import com.example.soldapple.post.repository.OptionRepository;
import com.example.soldapple.post.repository.PostRepository;
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

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final S3UploadUtil s3UploadUtil;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final OptionRepository optionRepository;
    private final CommentRepository commentRepository;


    //게시글 작성
    @Transactional
    public PostResponseDto postCreate(List<MultipartFile> multipartFiles,
                                      PostReqDto postReqDto,
                                      GetIPhonePriceResDto iphoneOption,
                                      GetMacbookPriceResDto macbookOption,
                                      Member member) throws IOException {
        //게시글 저장
        Post post = new Post(postReqDto, member);
        postRepository.save(post);

        //이미지 저장
        imgSave(multipartFiles, post);

        /*옵션항목들 저장*/
        if (iphoneOption == null) {
            //맥북일 때
            Opt options = new Opt(macbookOption, post);
            optionRepository.save(options);
            post.setOpt(options);

        } else {
            //아이폰일 때
            Opt options = new Opt(iphoneOption, post);
            optionRepository.save(options);
            post.setOpt(options);
        }
        //작성자가 프로필사진이 있는지 확인
        String avatarUrl = checkAvatar(post);
        //사용자가 해당 글에 좋아요 눌렀는지
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post, isLike, avatarUrl, commentDtos(post));
    }

//   public void postTest(){
//        System.out.println("테스트성공");
//    }

    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(List<MultipartFile> multipartFiles, Long postId, PostReqDto postReqDto, Member member) throws IOException {
        Post post = postRepository.findByPostIdAndMember(postId, member).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않거나 수정 권한이 없습니다.")
        );
        //내용 업데이트
        post.update(postReqDto);
        //기존 이미지 삭제
        deleteImg(post);
        //이미지 저장
        imgSave(multipartFiles, post);

        //이 게시글을 현재 사용자가 좋아요를 눌렀는지
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        String avatarUrl = checkAvatar(post);
        return new PostResponseDto(post, isLike, avatarUrl, commentDtos(post));
    }

    //게시글 삭제
    @Transactional
    public String postDelete(Long postId, Member member){
        Post post = postRepository.findByPostIdAndMember(postId,member).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않거나 삭제 권한이 없습니다.")
        );
        deleteImg(post);
        postRepository.deleteById(postId);
        return "게시글 삭제 완료";
    }

    //게시글 전체 조회 무한스크롤
    public Page<PostResponseDto> getAllPost(Pageable pageable) {
        return postRepository.findAllMyPost(pageable);
    }
    //게시글 전체 조회 + 검색 무한스크롤
    public Page<PostResponseDto> getAllPostWithSearch(Pageable pageable, String search) {
        return postRepository.findAllMyPostWithSearch(pageable, search);
    }

    //category + 내 좋아요 무한스크롤
    public Page<?> getAllPostWithCategory(Pageable pageable, String category) {
        Page<?> allPostWithCategory = postRepository.findAllPostWithCategory(pageable, category);
        return allPostWithCategory;
    }

    //category + 검색 정렬
    public Page<?> getAllPostWithCategoryWithSearch(Pageable pageable, String category, String search) {
        Page<?> allPostWithCategoryWithSearch = postRepository.findAllPostWithCategoryWithSearch(pageable, category, search);
        return allPostWithCategoryWithSearch;
    }

    //게시글 하나 조회
    public PostResponseDto onePost(Long postId, Member member) {
        Post post = postRepository.findByPostId(postId).orElseThrow(
                ()->new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );

        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        String avatarUrl = checkAvatar(post);

        return new PostResponseDto(post, isLike, avatarUrl, commentDtos(post));
    }

    //이미지 저장
    public void imgSave(List<MultipartFile> multipartFiles, Post post) throws IOException {
        List<Image> imageList = new ArrayList<>();

        if(!(multipartFiles.size()==0)){
            System.out.println(multipartFiles.get(0).getOriginalFilename());
            for(MultipartFile imgFile : multipartFiles){
                Map<String, String> img = s3UploadUtil.upload(imgFile, "test");
                Image image = new Image(img, post);
                imageRepository.save(image);
                imageList.add(image);
                imageRepository.save(image);
            }
        }
        post.setImages(imageList);
    }

    //댓글목록 dto 넣기
    private List<CommentResponseDto> commentDtos(Post post) {
        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<CommentResponseDto>();
        String avatarUrl;
        if(!(comments==null)){
            for (Comment comment : comments) {
                if (comment.getMember().getAvatarUrl()==null) {
                    avatarUrl = "https://s3.ap-northeast-2.amazonaws.com/myawsbucket.refined-stone/default/photoimg.png";
                } else{
                    avatarUrl = comment.getMember().getAvatarUrl();
                }
                commentResponseDtos.add(new CommentResponseDto(comment, avatarUrl));
            }
        }
        return commentResponseDtos;
    }

    //기존 사진 삭제
    public void deleteImg(Post post){
        List<Image> imageList = post.getImages();
        for (Image image : imageList) {
            s3UploadUtil.delete(image.getImgKey());
            imageRepository.delete(image);
        }
    }

    //프로필사진 있는지 확인
    private String checkAvatar(Post post) {
        if (post.getMember().getAvatarUrl()==null) {
            return "https://s3.ap-northeast-2.amazonaws.com/myawsbucket.refined-stone/default/photoimg.png";
        }
        else{
            return post.getMember().getAvatarUrl();
        }
    }
}