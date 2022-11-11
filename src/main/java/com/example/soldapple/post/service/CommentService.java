package com.example.soldapple.post.service;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.CommentReqDto;
import com.example.soldapple.post.dto.CommentResponseDto;
import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto commentCreate(Long postId, CommentReqDto commentReqDto, UserDetailsImpl userDetails) {


        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("post not exist")

        );
        Comment comment = new Comment(commentReqDto, post, userDetails.getMember());

        commentRepository.save(comment);

        return new CommentResponseDto(comment);


    }

    @Transactional
    public String commentDelete(Long commentId, Member member) {
        commentRepository.deleteByIdAndMember(commentId, member);
        return "Success";
    }

    @Transactional
    public CommentResponseDto commentEdit(Long commentId, CommentReqDto commentReqDto, Member member){
        Comment comment = commentRepository.findByIdAndMember(commentId, member).orElseThrow(
                () -> new IllegalArgumentException("comment not exist")

        );
        comment.CommentEdit(commentReqDto);
        return new CommentResponseDto(comment);
    }
}
