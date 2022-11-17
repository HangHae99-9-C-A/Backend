package com.example.soldapple.issues.repository;

import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssuesRepository extends JpaRepository<Issues, Long> {
    Optional<List<Issues>> findAllByCategoryOrderByCreatedAtDesc(String category);
    Optional<Issues> findByIssuesIdAndMember(Long issuesId, Member member);
    Optional<Issues> findByIssuesIdAndMemberNot(Long issuesId, Member member);
    Optional<Issues> findByIssuesId(Long issuesId);
    List<Issues> findAllByOrderByCreatedAtDesc();


    //myPage를 위해 추가 하였습니다
    List<Issues> findAllByMember(Member member);

}
