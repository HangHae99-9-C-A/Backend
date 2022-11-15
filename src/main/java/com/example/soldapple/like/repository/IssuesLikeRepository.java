package com.example.soldapple.like.repository;

import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.like.entity.IssuesLike;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuesLikeRepository extends JpaRepository<IssuesLike, Long> {
    void deleteByIssuesAndMember(Issues issues, Member member);
    boolean existsByIssuesAndMember(Issues issues, Member member);
}
