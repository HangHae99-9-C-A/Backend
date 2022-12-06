package com.example.soldapple.search.repository;

import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.responsedto.PostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Post, Long> {
    Optional<List<PostResponseDto>> findByTitleContaining(String keyword);
}
