package com.example.soldapple.post.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.like.entity.Like;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.PostReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn
    private Member member;
    @Column
    private String title;
    @Column
    private String category;
    @Column
    private Long expectPrice;
    @Column
    private Long userPrice;
    @Column
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Like> like;

    public Post(PostReqDto postReqDto, Member member) {
        this.member = member;
        this.title = postReqDto.getTitle();
        this.category = postReqDto.getCategory();
        this.expectPrice = postReqDto.getExpectPrice();
        this.userPrice = postReqDto.getUserPrice();
        this.content = postReqDto.getContent();
    }

    public void update(PostReqDto postReqDto) {
        this.title = postReqDto.getTitle();
        this.category = postReqDto.getCategory();
        this.expectPrice = postReqDto.getExpectPrice();
        this.userPrice = postReqDto.getUserPrice();
        this.content = postReqDto.getContent();
    }
}
