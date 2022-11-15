package com.example.soldapple.issues.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.like.entity.IssuesLike;
import com.example.soldapple.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "issues", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<IssuesImage> issuesimages;

    @Column
    private Long expectPrice;

    @Column
    private Long issuesUserPrice;

    @Column
    private String issuesContent;

    @OneToMany(mappedBy = "issues", cascade = CascadeType.REMOVE)
    List<IssuesComment> issuesComments;

    @OneToMany(mappedBy = "issues")
    private List<IssuesLike> issuesLikes;
    private Boolean isLike;
    private Long issuesLikecnt = 0L;

    public Issues(IssuesRequestDto issuesRequestDto, Member member){
        this.member = member;
        this.issuesTitle = issuesRequestDto.getIssuesTitle();
        this.category = issuesRequestDto.getCategory();
        this.expectPrice = issuesRequestDto.getExpectPrice();
        this.issuesUserPrice = issuesRequestDto.getIssuesUserPrice();
        this.issuesContent = issuesRequestDto.getIssuesContent();
    }

    public void updateIssuesLikeCnt(Long issuesLikecnt){this.issuesLikecnt = issuesLikecnt;}
}