package com.example.soldapple.quarydsl.post;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.responsedto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    //mypage에서 내가 like 한 postList  불러오기
    List<Post> findAllMyLikes(Member member);

    //post get all
    Page<PostResponseDto> findAllMyPost(Pageable pageable);
    // post get all - search
    Page<PostResponseDto> findAllMyPostWithSearch(Pageable pageable, String string);



    //post - category 정렬 + 내가 한 좋아요
    Page<?> findAllPostWithCategory(Pageable pageable, String category);

    //post -category - search
    Page<?> findAllPostWithCategoryWithSearch(Pageable pageable, String category, String search);

}
