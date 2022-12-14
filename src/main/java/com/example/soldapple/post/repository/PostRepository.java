package com.example.soldapple.post.repository;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.quarydsl.post.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    List<Post> findAllByCategoryOrderByCreatedAtDesc(String category);

    Optional<Post> findByPostIdAndMember(Long postId, Member member);

    Optional<Post> findByPostId(Long postId);

    List<Post> findAllByOrderByCreatedAtDesc();

    Optional<Post> findByPostIdAndMemberNot(Long postId, Member member);

    //myinfo를 위해 작성
    List<Post> findAllByMember(Member member);

    //Native Query
//    List<Post> findAllMyLikes(Member member);


}
