package com.example.soldapple.post.dto;


import com.example.soldapple.post.entity.SalesStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReqDto {
    private SalesStatus salesStatus;
    private String title;
    private String category;
    private Long expectPrice;
    private Long userPrice;
    private String content;
}
