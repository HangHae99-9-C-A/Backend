package com.example.soldapple.post.controller;


import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.service.MemberService;
import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping("")
    public String postCreate(@RequestBody @Valid PostReqDto postReqDto) {
        return postService.postCreate(postReqDto);
    }

    @GetMapping("/get")
    public List<Post> getPosts(){
        return postRepository.findAll();

    }

}
