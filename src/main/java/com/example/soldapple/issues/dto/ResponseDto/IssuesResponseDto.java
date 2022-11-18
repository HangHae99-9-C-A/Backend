package com.example.soldapple.issues.dto.ResponseDto;

import com.example.soldapple.global.TimeConverter;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.IssuesComment;
import com.example.soldapple.issues.entity.IssuesImage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import com.example.soldapple.issues.entity.IssuesOpt;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private IssuesOpt options;
    private List<IssuesImage> images;
    private Long expectPrice;
    private Long userPrice;
    private String content;

    private List<IssuesComment> comments;
    private Boolean isLike;
    private Long likeCnt;
    private Long memberId;

    public IssuesResponseDto(Issues issues, String avatarUrl, List<IssuesImage> issuesImages, Boolean isLike, Long likeCnt, IssuesOpt options){
        this.issuesId = issues.getIssuesId();
        this.title = issues.getIssuesTitle();
        this.nickname = issues.getMember().getNickname();
        this.avatarUrl = avatarUrl;
        this.memberId = issues.getMember().getId();
        this.createdAt = TimeConverter.convertTime ( issues.getCreatedAt () );
        this.modifiedAt = TimeConverter.convertTime ( issues.getModifiedAt () );
        this.category = issues.getCategory();
        this.options = options;
        this.images = issuesImages;
        this.expectPrice = issues.getExpectPrice();
        this.userPrice = issues.getIssuesUserPrice();
        this.content = issues.getIssuesContent();
        this.comments = issues.getIssuesComments();
        this.isLike = isLike;
        this.likeCnt = likeCnt;
        this.memberId = issues.getMember().getId();
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

        this.createdAt = TimeConverter.convertTime(issues.getCreatedAt());
        this.modifiedAt = TimeConverter.convertTime(issues.getModifiedAt());
        this.memberId = issues.getMember().getId();
    }
}
