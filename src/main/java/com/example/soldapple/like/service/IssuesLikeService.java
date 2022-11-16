package com.example.soldapple.like.service;

import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.repository.IssuesRepository;
import com.example.soldapple.like.entity.IssuesLike;
import com.example.soldapple.like.repository.IssuesLikeRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IssuesLikeService {
    private final MemberRepository memberRepository;
    private final IssuesRepository issuesRepository;
    private final IssuesLikeRepository issueslikeRepository;

    public String addLike(Long issuesId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new IllegalArgumentException("회원이 아닙니다.")
        );
        Issues issues = issuesRepository.findByIssuesIdAndMember(issuesId,member).orElseThrow(
                () -> new IllegalArgumentException("본인이 작성한 이의제기 글이거나 글이 존재하지 않습니다.")
        );
        Long likeCnt = issues.getIssuesLikeCnt();
        IssuesLike issueslike = new IssuesLike(issues, member);
        issues.likeUpdate(likeCnt+1);
        issueslikeRepository.save(issueslike);
        return "찜 성공";
    }

    public String deleteLike(Long issuesId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new IllegalArgumentException("회원이 아닙니다.")
        );
        Issues issues = issuesRepository.findByIssuesIdAndMember(issuesId,member).orElseThrow(
                () -> new IllegalArgumentException("본인이 작성한 이의제기 글이거나 글이 존재하지 않습니다.")
        );
        issueslikeRepository.deleteByIssuesAndMember(issues, member);

        Long likeCnt = issues.getIssuesLikeCnt();
        IssuesLike issuesLike = new IssuesLike(issues, member);
        issues.likeUpdate(likeCnt-1);
        return "찜 삭제";
    }
}
