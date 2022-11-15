package com.example.soldapple.post.service;


import com.example.soldapple.aws_s3.S3UploadUtil;
import com.example.soldapple.like.repository.LikeRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.entity.Image;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.ImageRepository;
import com.example.soldapple.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final S3UploadUtil s3UploadUtil;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    //게시글 작성
    @Transactional
    public PostResponseDto postCreate(List<MultipartFile> multipartFiles,
                                      PostReqDto postReqDto,
                                      Member member) throws IOException {
        //게시글 저장
        Post post = new Post(postReqDto, member);
        postRepository.save(post);

        return imgSave(multipartFiles, post, member);
    }
//    public void postTest(){
//        System.out.println("테스트성공");
//    }
    //게시글 전체 조회
    public List<PostResponseDto> allPosts(Member member) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtos = new ArrayList<PostResponseDto>();
        for (Post post : posts) {
            postResponseDtos.add(putImgsAndLikeToDto(post, member));
        }
        return postResponseDtos;
    }

    //게시글 하나 조회
    public PostResponseDto onePost(Long postId, Member member) {
        Post post = postRepository.findByPostId(postId).orElseThrow(
                ()->new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        return putImgsAndLikeToDto(post, member);
    }

    //게시글 카테고리 조회
    public List<PostResponseDto> categoryPost(String category, Member member) {
        List<PostResponseDto> postResponseDtos = new ArrayList<PostResponseDto>();
        List<Post> posts = postRepository.findAllByCategoryOrderByCreatedAtDesc(category).orElseThrow(
                ()->new RuntimeException("해당 카테고리가 없습니다.")
        );
        for (Post post : posts) {
            postResponseDtos.add(putImgsAndLikeToDto(post, member));
        }
        return postResponseDtos;
    }
    //게시글 수정
    @Transactional
    public PostResponseDto postEdit(List<MultipartFile> multipartFiles, Long postId, PostReqDto postReqDto, Member member)throws IOException{
        Post post = postRepository.findByPostIdAndMember(postId, member).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않거나 수정 권한이 없습니다.")
        );
        post.update(postReqDto);

        //기존 사진 삭제
        List<Image> imageList = post.getImages();
        for (Image image : imageList) {
            imageRepository.deleteById(image.getId());
            s3UploadUtil.delete(image.getImgKey());
        }
        return imgSave(multipartFiles, post, member);
    }


    //게시글 삭제
    @Transactional
    public String postDelete(Long postId, Member member){
        Post post = postRepository.findByPostIdAndMember(postId,member).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않거나 삭제 권한이 없습니다.")
        );

        List<Image> imageList = post.getImages();
        for (Image image : imageList) {
            s3UploadUtil.delete(image.getImgKey());
        }

        postRepository.delete(post);
        return "게시글 삭제 완료";
    }

    //이미지 저장
    public PostResponseDto imgSave(List<MultipartFile> multipartFiles, Post post, Member member) throws IOException{
        List<Image> imageList = new ArrayList<>();

        if(!(multipartFiles.size()==0)){
            System.out.println(multipartFiles.get(0).getOriginalFilename());
            for(MultipartFile imgFile : multipartFiles){
                Map<String, String> img = s3UploadUtil.upload(imgFile, "test");
                Image image = new Image(img, post);
                imageRepository.save(image);
                imageList.add(image);
            }
        }
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post, imageList, isLike);
    }
    //반복되는 조회로직
    public PostResponseDto putImgsAndLikeToDto(Post post, Member member){
        List<Image> imgList = new ArrayList<>();
        for(Image img:post.getImages()){
            imgList.add(img);
        }
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post,imgList,isLike);
    }
}