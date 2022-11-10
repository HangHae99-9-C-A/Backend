package com.example.soldapple.post.service;

import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public String postCreate(PostReqDto postReqDto) {
        Post post = new Post(postReqDto);

        postRepository.save(post);

        return "Success";
    }

    @Transactional
    public String postEdit(Long postId, PostReqDto postReqDto){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("post not exist")

        );
        post.PostEdit(postReqDto);
        return "Success";
    }

    @Transactional
    public String postDelete(Long postId){
        postRepository.deleteById(postId);
        return "Success";

    }

}
