package com.example.soldapple.issues.dto.ResponseDto;

import com.example.soldapple.issues.entity.Issues;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssuesResponseDto {
    private Long issuesId;
    private String issuesTitle;
    private String nickname;
    private String category;
    private Long expectPrice;
    private Long issuesUserPrice;
    private String issuesContent;

    public IssuesResponseDto(Issues issues){
        this.issuesId = issues.getIssuesId();
        this.issuesTitle = issues.getIssuesTitle();
        this.nickname = issues.getMember().getNickname();
        this.category = issues.getCategory();
        this.expectPrice = issues.getExpectPrice();
        this.issuesUserPrice = issues.getIssuesUserPrice();
        this.issuesContent = issues.getIssuesContent();
    }
}
