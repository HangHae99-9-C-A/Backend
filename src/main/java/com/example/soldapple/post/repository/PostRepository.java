package com.example.soldapple.post.repository;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findAllByCategory(String category);
    Optional<Post> findByCategoryAndId(String category, Long postId);
}
