package com.example.soldapple.issues.requestdto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssuesRequestDto {
    private String title;
    private String category;
    private String content;
    private Long expectPrice;
    private Long userPrice;
}
