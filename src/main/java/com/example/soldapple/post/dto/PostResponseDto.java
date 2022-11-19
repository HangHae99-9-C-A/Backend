package com.example.soldapple.post.dto;

import com.example.soldapple.global.TimeConverter;
import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.post.entity.Image;
import com.example.soldapple.post.entity.Opt;
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
    private String createdAt;
    private String modifiedAt;
    private String avatarUrl;
    private String category;
    private Opt options;
    private List<Image> images;
    private Long expectPrice;
    private Long userPrice;
    private String content;

    private List<Comment> comments;
    private Boolean isLike;
    private Long likeCnt;

    //프론트 요청으로...... 어쩔수없이 id 추가
    private Long memberId;


    public PostResponseDto(Post post,String avatarUrl, List<Image> images, Boolean isLike, Long likeCnt, Opt options){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.nickname = post.getMember().getNickname();
        this.memberId = post.getMember().getId();
        this.avatarUrl = avatarUrl;
        this.createdAt = TimeConverter.convertTime(post.getCreatedAt());
        this.modifiedAt = TimeConverter.convertTime(post.getModifiedAt());
        this.category = post.getCategory();
        this.options = options;
        this.images = images;
        this.expectPrice = post.getExpectPrice();
        this.userPrice = post.getUserPrice();
        this.content = post.getContent();
        this.comments = post.getComments();
        this.isLike = isLike;
        this.likeCnt = likeCnt;
    }

    //myinfo
    @QueryProjection
    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.nickname = post.getMember().getNickname();
        this.category = post.getCategory();
        this.images = post.getImages();
        //this.expectPrice = post.getExpectPrice();
        this.userPrice = post.getUserPrice();
        this.content = post.getContent();
        // this.comments = post.getComments();
        this.memberId = post.getMember().getId();

        //시간 추가
        this.createdAt = TimeConverter.convertTime(post.getCreatedAt());
        this.modifiedAt = TimeConverter.convertTime(post.getModifiedAt());

//        this.isLike = post.getIsLike();
    }
}
