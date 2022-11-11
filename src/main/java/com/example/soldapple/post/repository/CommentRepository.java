package com.example.soldapple.post.repository;

import com.example.soldapple.post.entity.Comment;
import com.example.soldapple.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndMember(Long commentId, Member member);
    Optional<Comment> deleteByIdAndMember(Long commentId, Member member);
}
