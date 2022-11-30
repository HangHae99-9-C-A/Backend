package com.example.soldapple.like.controller;

import com.example.soldapple.like.service.LikeService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/post/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}")
    public String addLike(@PathVariable Long postId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.addLike(postId, userDetails);
    }

    @DeleteMapping("/{postId}")
    public String deleteLike(@PathVariable Long postId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.deleteLike(postId, userDetails);
    }
}
