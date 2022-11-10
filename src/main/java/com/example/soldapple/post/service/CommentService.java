package com.example.soldapple.post.service;

import com.example.soldapple.post.dto.CommentReqDto;
import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public String commentCreate(Long postId, CommentReqDto commentReqDto) {


        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("post not exist")

        );
        Comment comment = new Comment(commentReqDto, post);

        commentRepository.save(comment);

        return "Success";


    }

    @Transactional
    public String commentDelete(Long commentId) {
        commentRepository.deleteById(commentId);
        return "Success";
    }

    @Transactional
    public String commentEdit(Long commentId, CommentReqDto commentReqDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("comment not exist")

        );
        comment.CommentEdit(commentReqDto);
        return "Success";
    }
}
