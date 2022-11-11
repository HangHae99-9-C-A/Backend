package com.example.soldapple.issues.dto.ResponseDto;

import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.IssuesComment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private List<IssuesComment> issuesComments;

    public IssuesResponseDto(Issues issues){
        this.issuesId = issues.getIssuesId();
        this.issuesTitle = issues.getIssuesTitle();
        this.nickname = issues.getMember().getNickname();
        this.category = issues.getCategory();
        this.expectPrice = issues.getExpectPrice();
        this.issuesUserPrice = issues.getIssuesUserPrice();
        this.issuesContent = issues.getIssuesContent();
        this.issuesComments = issues.getIssuesComments();
    }
}
