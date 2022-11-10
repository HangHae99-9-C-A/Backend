package com.example.soldapple.post.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReqDto {

    private String title;
    private String category;
    private String image;
    private int expectPrice;
    private int userPrice;
    private String content;


    public PostReqDto(String title, String category, String image, int expectPrice, int userPrice, String content){
        this.title = title;
        this.category = category;
        this.image = image;
        this.expectPrice = expectPrice;
        this.userPrice = userPrice;
        this.content = content;

    }
}
