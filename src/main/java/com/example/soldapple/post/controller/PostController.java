package com.example.soldapple.post.controller;


import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.service.PostService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public PostResponseDto postCreate(@RequestPart List<MultipartFile> multipartFiles,
                                      @RequestPart PostReqDto postReqDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        System.out.println("==========컨트롤러 지나는중==========");
        return postService.postCreate(multipartFiles, postReqDto, userDetails.getMember());
//        postService.postTest();
    }

    @GetMapping("/category/all")
    public List<PostResponseDto> allPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("==========컨트롤러 지나는중==========");
        return postService.allPosts(userDetails.getMember());
    }

    @GetMapping("/detail/{postId}")
    public PostResponseDto onePost(@PathVariable Long postId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("==========컨트롤러 지나는중==========");
        return postService.onePost(postId, userDetails.getMember());
    }

    @GetMapping("/category/{category}")
    public List<PostResponseDto> categoryPost(@PathVariable String category,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("==========컨트롤러 지나는중==========");
        return postService.categoryPost(category,userDetails.getMember());
    }

    @PutMapping("/{postId}")
    public PostResponseDto postEdit(@RequestPart List<MultipartFile> multipartFiles,
                                    @PathVariable Long postId,
                                    @RequestBody PostReqDto postReqDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails)throws IOException{
        System.out.println("==========컨트롤러 지나는중==========");
        return postService.postEdit(multipartFiles, postId, postReqDto, userDetails.getMember());
    }
    @DeleteMapping("/{postId}")
    public String postDelete(@PathVariable Long postId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("==========컨트롤러 지나는중==========");
        return postService.postDelete(postId, userDetails.getMember());
    }
}