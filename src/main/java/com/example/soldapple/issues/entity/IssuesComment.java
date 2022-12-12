package com.example.soldapple.issues.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.issues.requestdto.IssuesCommentRequestDto;
import com.example.soldapple.member.entity.Member;
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
    @JoinColumn(name = "issuesId")
    private Issues issues;

    @ManyToOne
    @JoinColumn
    private Member member;
    public IssuesComment(Issues issues, Member member, String issuesComment){
    this.issues = issues;
    this.member = member;
    this.issuesComment = issuesComment;
    }

    public void updateComment(IssuesCommentRequestDto issuesCommentRequestDto) {
        this.issuesComment = issuesCommentRequestDto.getIssuesComment();
    }
}
