package com.example.soldapple.post.requestdto;


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
    // 가격 보완 로직을 위해
    private Long optNum;
}
