package com.example.soldapple.post.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.requestdto.CommentReqDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends TimeStamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    //many comment to one post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    public Comment(CommentReqDto commentReqDto, Post post, Member member){
        this.comment = commentReqDto.getComment();
        this.post = post;
        this.member = member;

    }

    public void updateComment(CommentReqDto commentReqDto){
        this.comment = commentReqDto.getComment();
    }
}
