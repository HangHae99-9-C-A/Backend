package com.example.soldapple.like.controller;

import com.example.soldapple.like.service.LikeService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {//여긴 정수님이 짜심 Like패키지 안에 파일은 정수님이 짜서 주셨음요 like 서비스도?ㅇㅇ 저는 포스트랑 dto에 추가함.ㅋㅋㅋㅋ 재정님이죠
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
