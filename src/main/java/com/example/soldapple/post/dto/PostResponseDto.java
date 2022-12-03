package com.example.soldapple.post.dto;

import com.example.soldapple.post.entity.SalesStatus;
import com.example.soldapple.global.TimeConverter;
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
    private SalesStatus salesStatus;
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
    private Boolean myPost;

    private List<CommentResponseDto> comments;
    private Boolean isLike;
    private Long likeCnt;

    //프론트 요청으로...... 어쩔수없이 id 추가
    private Long memberId;


    public PostResponseDto(Post post, Boolean isLike, String avatarUrl, List<CommentResponseDto> commentResponseDtos, Boolean myPost){
        this.postId = post.getPostId();
        this.salesStatus = post.getSalesStatus();
        this.title = post.getTitle();
        this.nickname = post.getMember().getNickname();
        this.memberId = post.getMember().getId();
        this.avatarUrl = avatarUrl;
        this.createdAt = TimeConverter.convertTime(post.getCreatedAt());
        this.modifiedAt = TimeConverter.convertTime(post.getModifiedAt());
        this.category = post.getCategory();
        this.options = post.getOpt();
        this.images = post.getImages();
        this.expectPrice = post.getExpectPrice();
        this.userPrice = post.getUserPrice();
        this.content = post.getContent();
        this.comments = commentResponseDtos;
        this.isLike = isLike;
        this.likeCnt = post.getPostLikeCnt();
        this.myPost = myPost;
    }

    //myinfo
    @QueryProjection
    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.salesStatus = post.getSalesStatus();
        this.title = post.getTitle();
        this.nickname = post.getMember().getNickname();
        this.category = post.getCategory();
        this.images = post.getImages();
        //this.expectPrice = post.getExpectPrice();
        this.userPrice = post.getUserPrice();
        this.content = post.getContent();
        // this.comments = post.getComments();
        this.memberId = post.getMember().getId();
        this.likeCnt = post.getPostLikeCnt();
        //시간 추가
        this.createdAt = TimeConverter.convertTime(post.getCreatedAt());
        this.modifiedAt = TimeConverter.convertTime(post.getModifiedAt());

//        this.isLike = post.getIsLike();
    }
}
