package com.example.soldapple.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String imgUrl;
    @Lob
    private String imgKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    public Image(Map<String, String> image, Post post){
        this.imgUrl = image.get("url");
        this.imgKey = image.get("key");
        this.post = post;
    }
}
