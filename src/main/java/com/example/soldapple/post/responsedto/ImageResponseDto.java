package com.example.soldapple.post.responsedto;

import com.example.soldapple.post.entity.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class ImageResponseDto {
    private Long id;
    private String imgUrl;

    public ImageResponseDto(Image image){
        this.id = image.getId();
        this.imgUrl = image.getImgUrl();
    }
}
