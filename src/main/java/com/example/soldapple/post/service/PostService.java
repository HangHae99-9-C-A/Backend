package com.example.soldapple.post.service;

import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.entity.Member;
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
}
