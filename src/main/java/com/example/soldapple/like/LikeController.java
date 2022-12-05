package com.example.soldapple.like;

import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    /*게시글 좋아요*/
    @PostMapping("/posts/likes/{postId}") //게시글 좋아요 생성
    public String addLike(@PathVariable Long postId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.addLike(postId, userDetails);
    }

    @DeleteMapping("/posts/likes/{postId}") //게시글 좋아요 취소
    public String deleteLike(@PathVariable Long postId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.deleteLike(postId, userDetails);
    }

    /*이의제기글 좋아요*/
    @PostMapping("/issues/likes/{issuesId}") //이의제기 좋아요 생성
    public String addIssueLike(@PathVariable Long issuesId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.addIssueLike(issuesId, userDetails);
    }

    @DeleteMapping("/issues/likes/{issuesId}") //이의제기 좋아요 취소
    public String deleteIssueLike(@PathVariable Long issuesId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.deleteIssueLike(issuesId, userDetails);
    }
}
