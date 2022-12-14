package com.example.soldapple.issues.responsedto;

import com.example.soldapple.global.TimeConverter;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.IssuesImage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
public class IssuesResponseDto {

    private Long issuesId;
    private String title;
    private String nickname;
    private String createdAt;
    private String modifiedAt;
    private String avatarUrl;
    private String category;
    private IssuesOptResponseDto options;
    private List<IssuesImageResponseDto> images;
    private Long expectPrice;
    private Long userPrice;
    private String content;

    private List<IssuesCommentResponseDto> comments;
    private Boolean isLike;
    private Long likeCnt;
    private Long memberId;

    private Boolean myIssue;

    public IssuesResponseDto(Issues issues, List<IssuesCommentResponseDto> issuesCommentResponseDto, Boolean isLike, Boolean myIssue){
        this.issuesId = issues.getIssuesId();
        this.title = issues.getIssuesTitle();
        this.nickname = issues.getMember().getNickname();
        this.memberId = issues.getMember().getId();
        this.avatarUrl = issues.getMember().getAvatarUrl();
        this.createdAt = TimeConverter.convertTime ( issues.getCreatedAt () );
        this.modifiedAt = TimeConverter.convertTime ( issues.getModifiedAt () );
        this.category = issues.getCategory();
        this.options = new IssuesOptResponseDto(issues.getIssuesOpt());
        this.images = putImgDtos(issues);
        this.expectPrice = issues.getExpectPrice();
        this.userPrice = issues.getIssuesUserPrice();
        this.content = issues.getIssuesContent();
        this.comments = issuesCommentResponseDto;
        this.isLike = isLike;
        this.likeCnt = issues.getIssuesLikeCnt();
        this.myIssue = myIssue;
    }

    private List<IssuesImageResponseDto> putImgDtos(Issues issues) {
        List<IssuesImageResponseDto> imgDtos = new ArrayList<>();
        List<IssuesImage> imageList = issues.getIssuesImages();
        for (IssuesImage issuesImage : imageList) {
            imgDtos.add(new IssuesImageResponseDto(issuesImage));
        }
        return imgDtos;
    }

    @QueryProjection
    public IssuesResponseDto(Issues issues) {
        this.issuesId = issues.getIssuesId();
        this.title = issues.getIssuesTitle();
        this.nickname = issues.getMember().getNickname();
        this.category = issues.getCategory();
        this.expectPrice = issues.getExpectPrice();
        this.userPrice = issues.getIssuesUserPrice();
        this.content = issues.getIssuesContent();
        //   this.comments = issues.getIssuesComments();
        this.images = putImgDtos(issues);
        this.createdAt = TimeConverter.convertTime(issues.getCreatedAt());
        this.modifiedAt = TimeConverter.convertTime(issues.getModifiedAt());
        this.memberId = issues.getMember().getId();
        this.likeCnt = issues.getIssuesLikeCnt();
    }
}
