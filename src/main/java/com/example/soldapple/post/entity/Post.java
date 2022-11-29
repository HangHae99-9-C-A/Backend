package com.example.soldapple.post.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.like.entity.Like;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.PostReqDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private SalesStatus salesStatus;
    @ManyToOne
    @JoinColumn
    private Member member;
    private String title;

    private String category;
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Image> images;

    private Long expectPrice;
    private Long userPrice;
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Like> likes;

    private Boolean isLike;
    private Long postLikeCnt=0L;

    @OneToOne(mappedBy = "post", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Opt opt;

    public Post(PostReqDto postReqDto, Member member) {
        this.salesStatus = postReqDto.getSalesStatus();
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

    public void likeUpdate(Long likeCnt) {
        this.postLikeCnt = likeCnt;
    }

    public void soldOut(PostReqDto postReqDto) {
        this.salesStatus = postReqDto.getSalesStatus();
    }
}