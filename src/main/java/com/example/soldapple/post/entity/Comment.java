package com.example.soldapple.post.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.CommentReqDto;
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
    @JsonIgnore
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn
    private Member member;

    private String nickname;

    public Comment(CommentReqDto commentReqDto, Post post, Member member){
        this.comment = commentReqDto.getComment();
        this.post = post;
        this.nickname = member.getNickname();
        this.member = member;

    }

    public void CommentEdit(CommentReqDto commentReqDto){
        this.comment = commentReqDto.getComment();

    }
}
