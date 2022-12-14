package com.example.soldapple.issues.responsedto;

import com.example.soldapple.issues.entity.IssuesImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssuesImageResponseDto {
    private Long id;
    private String imgUrl;

    public IssuesImageResponseDto(IssuesImage image){
        this.id = image.getId();
        this.imgUrl = image.getImgUrl();
    }
}
