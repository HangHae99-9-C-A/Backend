package com.example.soldapple.issues.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
public class IssuesImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String imgUrl;
    private String imgKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issuesId")
    private Issues issues;

    public IssuesImage(Map<String,String>issuesImage, Issues issues){
        this.imgUrl = issuesImage.get("url");
        this.imgKey = issuesImage.get("key");
        this.issues = issues;
    }
}
