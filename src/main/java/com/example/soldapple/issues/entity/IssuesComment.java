package com.example.soldapple.issues.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssuesComment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issuesCommentId;

    @Column(nullable = false)
    private String issuesComment;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "issuesId")
    private Issues issues;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Member member;
    private String nickname;
    public IssuesComment(Issues issues, Member member, String issuesComment){
    this.issues = issues;
    this.member = member;
    this.nickname = member.getNickname();
    this.issuesComment = issuesComment;
    }
}
