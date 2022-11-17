package com.example.soldapple.quarydsl;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {

  //  List<PostResponseDto> findAllMyTest(Member member);
    List<Post> findAllMyTest(Member member);

}
