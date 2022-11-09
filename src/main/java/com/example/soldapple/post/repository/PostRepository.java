package com.example.soldapple.post.repository;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
