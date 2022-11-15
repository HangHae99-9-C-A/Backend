package com.example.soldapple.issues.entity;

import com.example.soldapple.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class IssuesImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String imgUrl;
    private String imgKey;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issuesId")
    private Issues issues;

    public IssuesImage(Map<String,String>issuesImage, Issues issues){
        this.imgUrl = issuesImage.get("url");
        this.imgKey = issuesImage.get("key");
        this.issues = issues;
    }
}
