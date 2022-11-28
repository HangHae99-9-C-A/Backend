package com.example.soldapple.post.controller;

import com.example.soldapple.post.dto.CommentReqDto;
import com.example.soldapple.post.dto.CommentResponseDto;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.service.CommentService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

//    @PostMapping("/{postId}")
//    public String commentCreate(@RequestBody @Valid CommentReqDto commentReqDto) {
//        return commentService.commentCreate(commentReqDto);
//    }

    @PostMapping("/{postId}")
    public CommentResponseDto commentCreate(@PathVariable Long postId, @RequestBody @Valid CommentReqDto commentReqDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.commentCreate(postId, commentReqDto,  userDetails);
    }

    @DeleteMapping("/{commentId}")
    public String commentDelete(@PathVariable Long commentId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.commentDelete(commentId, userDetails.getMember());

    }

    @PutMapping("/{commentId}")
    public CommentResponseDto commentEdit(@PathVariable Long commentId, @RequestBody @Valid CommentReqDto commentReqDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.commentEdit(commentId, commentReqDto, userDetails.getMember());
    }
}
