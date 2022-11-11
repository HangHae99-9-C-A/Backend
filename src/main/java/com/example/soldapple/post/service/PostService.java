package com.example.soldapple.post.service;

import com.example.soldapple.like.repository.LikeRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    //게시글 작성
    @Transactional
    public PostResponseDto postCreate(PostReqDto postReqDto, Member member) {
        Post post = new Post(postReqDto, member);
        postRepository.save(post);
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post,isLike);
    }
    //게시글 전체 조회
    public List<PostResponseDto> allPosts(Member member) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtos = new ArrayList<PostResponseDto>();
        for (Post post : posts) {
            Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
            postResponseDtos.add(new PostResponseDto(post,isLike));
        }
        return postResponseDtos;
    }

    //게시글 하나 조회
    public PostResponseDto onePost(Long postId, Member member) {
        Post post = postRepository.findByPostId(postId).orElseThrow(
                ()->new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        return new PostResponseDto(post,isLike);
    }

    //게시글 카테고리 조히
    public List<PostResponseDto> categoryPost(String category, Member member) {
        List<PostResponseDto> postResponseDtos = new ArrayList<PostResponseDto>();
        List<Post> posts = postRepository.findAllByCategoryOrderByCreatedAtDesc(category).orElseThrow(
                ()->new RuntimeException("해당 카테고리가 없습니다.")
        );
        for (Post post : posts) {
            Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
            postResponseDtos.add(new PostResponseDto(post, isLike));
        }

        return postResponseDtos;
    }
    //게시글 수정
    @Transactional
    public PostResponseDto postEdit(Long postId, PostReqDto postReqDto, Member member){
        Post post = postRepository.findByPostIdAndMember(postId, member).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않거나 수정 권한이 없습니다.")
        );
        Boolean isLike = likeRepository.existsByMemberAndPost(member, post);
        post.update(postReqDto);
        return new PostResponseDto(post, isLike);
    }
    //게시글 삭제
    @Transactional
    public String postDelete(Long postId, Member member){
        Post post = postRepository.findByPostIdAndMember(postId,member).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않거나 삭제 권한이 없습니다.")
        );
        postRepository.delete(post);
        return "게시글 삭제 완료";
    }
}
