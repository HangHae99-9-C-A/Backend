package com.example.soldapple.issues.dto.ResponseDto;

import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.IssuesComment;
import com.example.soldapple.issues.entity.IssuesImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class IssuesResponseDto {
    private Long id;
    private String title;
    private String nickname;
    private String category;
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
        this.category = issues.getCategory();
        this.images = issuesImages;
        this.expectPrice = issues.getExpectPrice();
        this.userPrice = issues.getIssuesUserPrice();
        this.content = issues.getIssuesContent();
        this.comments = issues.getIssuesComments();
        this.isLike = isLike;
        this.likeCnt = likeCnt;
        this.memberId = issues.getMember().getId();
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
