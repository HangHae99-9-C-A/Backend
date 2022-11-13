package com.example.soldapple.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String imgUrl;
    private String imgKey;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    public Image(Map<String, String> image, Post post){
        this.imgUrl = image.get("url");
        this.imgKey = image.get("key");
        this.post = post;
    }
}
