package com.example.soldapple.like.controller;

import com.example.soldapple.like.service.LikeService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class IssuesLikeController {
    private final LikeService likeService;

    @PostMapping("/{issuesId}")
    public String addLike(@PathVariable Long issuesId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.addLike(issuesId, userDetails);
    }

    @DeleteMapping("/{issuesId}")
    public String deleteLike(@PathVariable Long issuesId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.deleteLike(issuesId, userDetails);
    }
}