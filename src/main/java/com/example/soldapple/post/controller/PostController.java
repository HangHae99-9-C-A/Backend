package com.example.soldapple.post.controller;


import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.service.PostService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public PostResponseDto postCreate(@RequestBody PostReqDto postReqDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.postCreate(postReqDto, userDetails.getMember());
    }

    @GetMapping("")
    public List<PostResponseDto> allPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.allPosts(userDetails.getMember());
    }

    @GetMapping("/detail/{postId}")
    public PostResponseDto onePost(@PathVariable Long postId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.onePost(postId, userDetails.getMember());
    }

    @GetMapping("/category/{category}")
    public List<PostResponseDto> categoryPost(@PathVariable String category,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.categoryPost(category,userDetails.getMember());
    }

    @PutMapping("/{postId}")
    public PostResponseDto postEdit(@PathVariable Long postId,
                           @RequestBody PostReqDto postReqDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.postEdit(postId, postReqDto, userDetails.getMember());
    }

    @DeleteMapping("{postId}")
    public String postDelete(@PathVariable Long postId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.postDelete(postId, userDetails.getMember());
    }
}