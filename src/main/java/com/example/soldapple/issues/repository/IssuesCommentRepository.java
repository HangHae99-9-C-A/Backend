package com.example.soldapple.issues.repository;

import com.example.soldapple.issues.entity.IssuesComment;
import com.example.soldapple.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssuesCommentRepository extends JpaRepository<IssuesComment, Long> {

    Optional<IssuesComment> findByIssuesCommentIdAndMember(Long issuesCommentId, Member member);
}
