package com.example.soldapple.post.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReqDto {
    private String title;
    private String category;
    private Long expectPrice;
    private Long userPrice;
    private String content;
}
