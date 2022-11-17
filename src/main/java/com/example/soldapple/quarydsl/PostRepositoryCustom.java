package com.example.soldapple.quarydsl;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.dto.PostResponseDto;
import com.example.soldapple.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    //mypage에서 내가 like 한 postList  불러오기
    List<Post> findAllMyLikes(Member member);

    Page<PostResponseDto> findMyQuery(Pageable pageable);
}
