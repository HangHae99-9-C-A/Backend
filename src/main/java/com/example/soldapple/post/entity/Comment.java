package com.example.soldapple.post.entity;

import com.example.soldapple.post.dto.CommentReqDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    //many comment to one post

    @ManyToOne
    @JoinColumn(name = "post")
    @JsonIgnore
    private Post post;

    public Comment(CommentReqDto commentReqDto, Post post){
        this.comment = commentReqDto.getComment();
        this.post = post;


    }

    public void CommentEdit(CommentReqDto commentReqDto){
        this.comment = commentReqDto.getComment();

    }
}
