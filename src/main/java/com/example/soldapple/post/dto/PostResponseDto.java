package com.example.soldapple.post.dto;

import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.post.entity.Image;
import com.example.soldapple.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String nickname;
    private String category;
    private List<Image> images;
    private Long expectPrice;
    private Long userPrice;
    private String content;

    private List<Comment> comments;
    private Boolean isLike;

    //프론트 요청으로...... 어쩔수없이 id 추가
    private Long memberId;

    private Long likeCnt;

    public PostResponseDto(Post post, List<Image> images, Boolean isLike, Long likeCnt){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.nickname =post.getMember().getNickname();
        this.category = post.getCategory();
        this.images = images;
        this.expectPrice = post.getExpectPrice();
        this.userPrice = post.getUserPrice();
        this.content = post.getContent();
        this.comments = post.getComments();
        this.isLike = isLike;
        this.likeCnt = likeCnt;

        //프론트 요청
        this.memberId = post.getMember().getId();
    }

    //myinfo
    @QueryProjection
    public PostResponseDto(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.nickname =post.getMember().getNickname();
        this.category = post.getCategory();
        this.images = post.getImages();
        this.expectPrice = post.getExpectPrice();
        this.userPrice = post.getUserPrice();
        this.content = post.getContent();
        this.comments = post.getComments();
        this.memberId = post.getMember().getId();

        //like는 연관관계를 수정해야 합니다.
        // 정상적인 로직이 아님
//        this.isLike = post.getIsLike();
    }
}
