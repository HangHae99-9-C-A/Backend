package com.example.soldapple.issues.entity;

import com.example.soldapple.global.TimeStamped;
import com.example.soldapple.issues.requestdto.IssuesRequestDto;
import com.example.soldapple.like.entity.IssuesLike;
import com.example.soldapple.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToMany(mappedBy = "issues", cascade = CascadeType.REMOVE)
    private List<IssuesImage> issuesImages;

    @Column
    private Long expectPrice;

    @Column
    private Long issuesUserPrice;

    @Column
    private String issuesContent;

    @OneToMany(mappedBy = "issues", cascade = CascadeType.REMOVE)
    List<IssuesComment> issuesComments;

    @OneToMany(mappedBy = "issues", cascade = CascadeType.REMOVE)
    private List<IssuesLike> issuesLikes;
    private Long issuesLikeCnt = 0L;

    @OneToOne(mappedBy = "issues",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private IssuesOpt issuesOpt;

    public Issues(IssuesRequestDto issuesRequestDto, Member member) {
        this.member = member;
        this.issuesTitle = issuesRequestDto.getTitle();
        this.category = issuesRequestDto.getCategory();
        this.expectPrice = issuesRequestDto.getExpectPrice();
        this.issuesUserPrice = issuesRequestDto.getUserPrice();
        this.issuesContent = issuesRequestDto.getContent();
    }

    public void update(IssuesRequestDto issuesRequestDto) {
        this.issuesContent = issuesRequestDto.getContent();
    }

    public void likeUpdate(Long likeCnt) {
        this.issuesLikeCnt = likeCnt;
    }

    public void addOpt(IssuesOpt options) {
        this.issuesOpt = options;
    }

    public void updateImg(List<IssuesImage> imageList) {
        this.issuesImages=imageList;
    }
}