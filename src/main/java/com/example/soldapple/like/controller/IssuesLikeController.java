package com.example.soldapple.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class IssuesLikeController {
//    private final LikeService likeService;
//
//    @PostMapping("/{issuesId}")
//    public String addLike(@PathVariable Long issuesId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return likeService.addLike(issuesId, userDetails);
//    }
//
//    @DeleteMapping("/{issuesId}")
//    public String deleteLike(@PathVariable Long issuesId, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return likeService.deleteLike(issuesId, userDetails);
//    }
}
