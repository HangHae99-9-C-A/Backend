package com.example.soldapple.like.controller;

import com.example.soldapple.like.service.IssuesLikeService;
import com.example.soldapple.security.user.UserDetailsImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "issues-like-controller", description = "이의제기 좋아요 API")
@RestController
@RequestMapping("/issue/likes")
@RequiredArgsConstructor
public class IssuesLikeController {
    private final IssuesLikeService issuesLikeService;

    @PostMapping("/{issuesId}")
    public String addLike(@PathVariable Long issuesId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return issuesLikeService.addLike(issuesId, userDetails);
    }

    @DeleteMapping("/{issuesId}")
    public String deleteLike(@PathVariable Long issuesId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesLikeService.deleteLike(issuesId, userDetails);
    }
}
