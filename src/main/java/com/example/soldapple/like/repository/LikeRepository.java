package com.example.soldapple.like.repository;

import com.example.soldapple.like.entity.Like;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>{
    void deleteByPostAndMember(Post post, Member member);
    boolean existsByMemberAndPost(Member member, Post post);
}
