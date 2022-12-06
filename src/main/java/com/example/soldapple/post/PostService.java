package com.example.soldapple.post;


import com.example.soldapple.aws_s3.S3UploadUtil;
import com.example.soldapple.create_price.dto.GetIPhonePriceResDto;
import com.example.soldapple.create_price.dto.GetMacbookPriceResDto;
import com.example.soldapple.error.CustomException;
import com.example.soldapple.like.repository.LikeRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.requestdto.CommentReqDto;
import com.example.soldapple.post.responsedto.CommentResponseDto;
import com.example.soldapple.post.requestdto.PostReqDto;
import com.example.soldapple.post.responsedto.PostResponseDto;
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

import static com.example.soldapple.error.ErrorCode.CANNOT_FIND_POST_NOT_EXIST;

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
    public PostResponseDto postCreate(List<MultipartFile> multipartFiles,
                                      PostReqDto postReqDto,
                                      GetIPhonePriceResDto iphoneOption,
                                      GetMacbookPriceResDto macbookOption,
                                      Member member) throws IOException {
        //게시글 저장
        Post post = new Post(postReqDto, member);
        postRepository.save(post);
        Boolean myPost = post.getMember().getId().equals(member.getId());

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
        //이미지 저장
        imgSave(multipartFiles, post);

        //사용자가 해당 글에 좋아요 눌렀는지
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post, isLike, commentDtos(post, member.getId()), myPost);
    }

    //게시글 수정
    public PostResponseDto updatePost(List<MultipartFile> multipartFiles, Long postId, PostReqDto postReqDto, Member member) throws IOException {
        Post post = postRepository.findByPostIdAndMember(postId, member).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        Boolean myPost = post.getMember().getId().equals(member.getId());
        //내용 업데이트
        post.update(postReqDto);

        if (!(multipartFiles == null)) {
            //기존 이미지 삭제
            List<Image> imageList = post.getImages();
            for (Image image : imageList) {
                s3UploadUtil.delete(image.getImgKey());
                imageRepository.delete(image);
            }
            //이미지 저장
            imgSave(multipartFiles, post);
        }

        //이 게시글을 현재 사용자가 좋아요를 눌렀는지
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post, isLike, commentDtos(post, member.getId()), myPost);
    }

    //판매완료(판매상태 변경)
    public PostResponseDto updateStatus(Long postId, Member member) {
        Post post = postRepository.findByPostId(postId).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        Boolean myPost = post.getMember().getId().equals(member.getId());
        post.soldOut();
        //이 게시글을 현재 사용자가 좋아요를 눌렀는지
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post, isLike, commentDtos(post, member.getId()), myPost);
    }

    //게시글 삭제
    public String postDelete(Long postId, Member member) {
        Post post = postRepository.findByPostIdAndMember(postId, member).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        postRepository.deleteById(postId);
        return "게시글 삭제 완료";
    }

    //게시글 전체 조회 무한스크롤
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPost(Pageable pageable) {
        return postRepository.findAllMyPost(pageable);
    }

    //게시글 전체 조회 + 검색 무한스크롤
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPostWithSearch(Pageable pageable, String search) {
        return postRepository.findAllMyPostWithSearch(pageable, search);
    }

    //category + 내 좋아요 무한스크롤
    @Transactional(readOnly = true)
    public Page<?> getAllPostWithCategory(Pageable pageable, String category) {
        Page<?> allPostWithCategory = postRepository.findAllPostWithCategory(pageable, category);
        return allPostWithCategory;
    }

    //category + 검색 정렬
    @Transactional(readOnly = true)
    public Page<?> getAllPostWithCategoryWithSearch(Pageable pageable, String category, String search) {
        Page<?> allPostWithCategoryWithSearch = postRepository.findAllPostWithCategoryWithSearch(pageable, category, search);
        return allPostWithCategoryWithSearch;
    }


    //게시글 하나 조회
    public PostResponseDto onePost(Long postId, Member member) {
        Post post = postRepository.findByPostId(postId).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        Boolean myPost = post.getMember().getId().equals(member.getId());
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post, isLike, commentDtos(post, member.getId()), myPost);
    }

    //이미지 저장
    public void imgSave(List<MultipartFile> multipartFiles, Post post) throws IOException {
        List<Image> imageList = new ArrayList<>();

        for (MultipartFile imgFile : multipartFiles) {
            Map<String, String> img = s3UploadUtil.upload(imgFile, "post-img");
            Image image = new Image(img, post);
            imageRepository.save(image);
            imageList.add(image);
        }
        post.updateImg(imageList);
    }

    //댓글목록 dto 넣기
    public List<CommentResponseDto> commentDtos(Post post, Long memberId) {
        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<CommentResponseDto>();
        String avatarUrl;
        Boolean myComment;
        if (!(comments == null)) {
            for (Comment comment : comments) {
                myComment = comment.getMember().getId().equals(memberId);
                commentResponseDtos.add(new CommentResponseDto(comment, myComment));
            }
        }
        return commentResponseDtos;
    }

    /*게시글 댓글*/
    //게시글 댓글 작성
    @Transactional
    public CommentResponseDto commentCreate(Long postId, CommentReqDto commentReqDto, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("post not exist")
        );
        Comment comment = new Comment(commentReqDto, post, member);
        commentRepository.save(comment);
        Boolean myComment = comment.getMember().getId().equals(member.getId());
        return new CommentResponseDto(comment, myComment);
    }

    //게시글 댓글 수정
    @Transactional
    public CommentResponseDto commentEdit(Long commentId, CommentReqDto commentReqDto, Member member) {
        Comment comment = commentRepository.findByIdAndMember(commentId, member).orElseThrow(
                () -> new IllegalArgumentException("comment not exist")
        );
        Boolean myComment = comment.getMember().getId().equals(member.getId());
        comment.CommentEdit(commentReqDto);
        return new CommentResponseDto(comment, myComment);
    }

    //게시글 댓글 삭제
    @Transactional
    public String commentDelete(Long commentId, Member member) {
        commentRepository.deleteByIdAndMember(commentId, member);
        return "댓글 삭제 완료";
    }
}