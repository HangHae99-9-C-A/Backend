package com.example.soldapple.post.controller;

import com.example.soldapple.post.dto.CommentReqDto;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    public String commentCreate(@PathVariable Long postId,@RequestBody @Valid CommentReqDto commentReqDto){
        return commentService.commentCreate(postId, commentReqDto);
    }

    @DeleteMapping("/{commentId}")
    public String commentDelete(@PathVariable Long commentId){
        return commentService.commentDelete(commentId);

    }

    @PutMapping("/{commentId}")
    public String commentEdit(@PathVariable Long commentId,@RequestBody @Valid CommentReqDto commentReqDto){
        return commentService.commentEdit(commentId, commentReqDto);

    }



}
