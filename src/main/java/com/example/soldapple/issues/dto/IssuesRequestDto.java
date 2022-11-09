package com.example.soldapple.issues.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssuesRequestDto {
    private String issuesTitle;
    private String category;
    private String issuesContent;
    private Long expectPrice;
    private Long issuesUserPrice;
}
