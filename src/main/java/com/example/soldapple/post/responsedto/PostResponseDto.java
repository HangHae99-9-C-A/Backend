package com.example.soldapple.post.responsedto;

import com.example.soldapple.global.TimeConverter;
import com.example.soldapple.post.entity.Image;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.entity.SalesStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
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
    private OptResponseDto options;
    private List<ImageResponseDto> images;
    private Long expectPrice;
    private Long userPrice;
    private String content;
    private Boolean myPost;

    private List<CommentResponseDto> comments;
    private Boolean isLike;
    private Long likeCnt;
    private Long memberId;


    public PostResponseDto(Post post, List<CommentResponseDto> commentResponseDtos, Boolean isLike, Boolean myPost) {
        this.postId = post.getPostId();
        this.salesStatus = post.getSalesStatus();
        this.title = post.getTitle();
        this.nickname = post.getMember().getNickname();
        this.memberId = post.getMember().getId();
        this.avatarUrl = post.getMember().getAvatarUrl();
        this.createdAt = TimeConverter.convertTime(post.getCreatedAt());
        this.modifiedAt = TimeConverter.convertTime(post.getModifiedAt());
        this.category = post.getCategory();
        this.options = new OptResponseDto(post.getOpt());
        this.images = putImgDtos(post);
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
        this.images = putImgDtos(post);
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

    private List<ImageResponseDto> putImgDtos(Post post) {
        List<ImageResponseDto> imgDtos = new ArrayList<>();
        List<Image> images = post.getImages();
        for (Image image : images) {
            imgDtos.add(new ImageResponseDto(image));
        }
        return imgDtos;
    }
}
