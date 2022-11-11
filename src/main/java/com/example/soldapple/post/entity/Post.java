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
    private String title;
    private String category;
    private Long expectPrice;
    private Long userPrice;
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Like> like;

    private Boolean isLike;
    private Long postLikeCount = 0L;

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

    public void updatePostLikeCnt(Long postLikeCount) {
        this.postLikeCount = postLikeCount;
    }
}
