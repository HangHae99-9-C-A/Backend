package com.example.soldapple.issues.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Issues extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issuesId;

    @ManyToOne
    @JoinColumn
    private Member member;

    @Column
    private String issuesTitle;

    @Column
    private String category;

    @Column
    private Long expectPrice;

    @Column
    private Long issuesUserPrice;

    @Column
    private String issuesContent;

    @OneToMany(mappedBy = "issues", cascade = CascadeType.REMOVE)
    List<IssuesComment> issuesComments;

    public Issues(IssuesRequestDto issuesRequestDto, Member member){
        this.member = member;
        this.issuesTitle = issuesRequestDto.getIssuesTitle();
        this.category = issuesRequestDto.getCategory();
        this.expectPrice = issuesRequestDto.getExpectPrice();
        this.issuesUserPrice = issuesRequestDto.getIssuesUserPrice();
        this.issuesContent = issuesRequestDto.getIssuesContent();
    }
}
