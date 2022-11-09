package com.example.soldapple.post.entity;

import com.example.soldapple.post.dto.PostReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private String image;
    private int expectPrice;
    private int userPrice;
    private String content;

    // one to many
    // many to one
    // 여기가 post
    // one post to mant comment
    // 반대는 many comment to one post

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

    public Post(PostReqDto postReqDto) {
        this.title = postReqDto.getTitle();
        this.category = postReqDto.getCategory();
        this.image = postReqDto.getImage();
        this.expectPrice = postReqDto.getExpectPrice();
        this.userPrice = postReqDto.getUserPrice();
        this.content = postReqDto.getContent();

    }

    public void PostEdit(PostReqDto postReqDto){
        this.title = postReqDto.getTitle();
        this.category = postReqDto.getCategory();
        this.image = postReqDto.getImage();
        this.expectPrice = postReqDto.getExpectPrice();
        this.userPrice = postReqDto.getUserPrice();
        this.content = postReqDto.getContent();

    }
}
