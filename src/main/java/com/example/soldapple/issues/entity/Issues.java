package com.example.soldapple.issues.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.issues.dto.IssuesRequestDto;
import com.example.soldapple.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Issues extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issuesId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private String issuesTitle;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Long expectPrice;

    @Column(nullable = false)
    private Long issuesUserPrice;

    @Column(nullable = false)
    private String issuesContent;

    public Issues(IssuesRequestDto issuesRequestDto, Member member){
        this.member = member;
        this.issuesTitle = issuesRequestDto.getIssuesTitle();
        this.category = issuesRequestDto.getCategory();
        this.expectPrice = issuesRequestDto.getExpectPrice();
        this.issuesUserPrice = issuesRequestDto.getIssuesUserPrice();
        this.issuesContent = issuesRequestDto.getIssuesContent();
    }

}
