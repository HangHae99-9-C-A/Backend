package com.example.soldapple.like.entity;

import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "IssuesLikes")
public class IssuesLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "issues")
    private Issues issues;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "memberId")
    private Member member;

    public IssuesLike(Issues issues, Member member){
        this.issues = issues;
        this.member = member;
    }
}
