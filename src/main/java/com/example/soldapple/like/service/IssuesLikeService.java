package com.example.soldapple.like.service;

import com.example.soldapple.error.CustomException;
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

import static com.example.soldapple.error.ErrorCode.CANNOT_FIND_POST_NOT_EXIST;
import static com.example.soldapple.error.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class IssuesLikeService {
    private final MemberRepository memberRepository;
    private final IssuesRepository issuesRepository;
    private final IssuesLikeRepository issueslikeRepository;

    public String addLike(Long issuesId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Issues issues = issuesRepository.findByIssuesIdAndMemberNot(issuesId,member).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        Long likeCnt = issues.getIssuesLikeCnt();
        IssuesLike issueslike = new IssuesLike(issues, member);
        issues.likeUpdate(likeCnt+1);
        issueslikeRepository.save(issueslike);
        return "찜 성공";
    }

    public String deleteLike(Long issuesId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Issues issues = issuesRepository.findByIssuesIdAndMemberNot(issuesId,member).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        issueslikeRepository.deleteByIssuesAndMember(issues, member);
        Long likeCnt = issues.getIssuesLikeCnt();
        issues.likeUpdate(likeCnt-1);
        return "찜 삭제";
    }
}
