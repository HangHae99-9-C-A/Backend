package com.example.soldapple.issues.dto.ResponseDto;

import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.IssuesComment;
import com.example.soldapple.issues.entity.IssuesImage;
import com.example.soldapple.post.entity.Opt;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
public class IssuesResponseDto {
    private Long id;
    private String title;
    private String nickname;
    private String avatarUrl;
    private String category;
    private Opt options;
    private List<IssuesImage> images;
    private Long expectPrice;
    private Long userPrice;
    private String content;

    private List<IssuesComment> comments;
    private Boolean isLike;
    private Long likeCnt;
    private Long memberId;

    public IssuesResponseDto(Issues issues, List<IssuesImage> issuesImages, Boolean isLike, Long likeCnt){
        this.id = issues.getIssuesId();
        this.title = issues.getIssuesTitle();
        this.nickname = issues.getMember().getNickname();
        this.avatarUrl = issues.getMember().getAvatarUrl();
        this.memberId = issues.getMember().getId();
        this.category = issues.getCategory();
        this.options = issues.getOpt();
        this.images = issuesImages;
        this.expectPrice = issues.getExpectPrice();
        this.userPrice = issues.getIssuesUserPrice();
        this.content = issues.getIssuesContent();
        this.comments = issues.getIssuesComments();
        this.isLike = isLike;
        this.likeCnt = likeCnt;
    }
    public IssuesResponseDto(Issues issues){
        this.id = issues.getIssuesId();
        this.title = issues.getIssuesTitle();
        this.nickname = issues.getMember().getNickname();
        this.category = issues.getCategory();
        this.expectPrice = issues.getExpectPrice();
        this.userPrice = issues.getIssuesUserPrice();
        this.content = issues.getIssuesContent();
        this.comments = issues.getIssuesComments();
    }
}
