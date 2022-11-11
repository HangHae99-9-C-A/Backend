package com.example.soldapple.post.controller;

import com.example.soldapple.post.dto.CommentReqDto;
import com.example.soldapple.post.dto.CommentResponseDto;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.service.CommentService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

//    @PostMapping("/{postId}")
//    public String commentCreate(@RequestBody @Valid CommentReqDto commentReqDto) {
//        return commentService.commentCreate(commentReqDto);
//    }

    @PostMapping("/{postId}")
    public CommentResponseDto commentCreate(@PathVariable Long postId, @RequestBody @Valid CommentReqDto commentReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.commentCreate(postId, commentReqDto,  userDetails);
    }

    @DeleteMapping("/{commentId}")
    public String commentDelete(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.commentDelete(commentId, userDetails.getMember());

    }

    @PutMapping("/{commentId}")
    public CommentResponseDto commentEdit(@PathVariable Long commentId, @RequestBody @Valid CommentReqDto commentReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.commentEdit(commentId, commentReqDto, userDetails.getMember());

    }



}
