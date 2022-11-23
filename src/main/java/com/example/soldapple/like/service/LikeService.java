package com.example.soldapple.like.service;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.like.entity.Like;
import com.example.soldapple.like.repository.LikeRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.soldapple.error.ErrorCode.CANNOT_FIND_POST_NOT_EXIST;
import static com.example.soldapple.error.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public String addLike(Long postId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Post post = postRepository.findByPostIdAndMemberNot(postId, member).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );

        Long likeCnt = post.getPostLikeCnt();
        Like like = new Like(post, member);
        post.likeUpdate(likeCnt+1);
        likeRepository.save(like);
        return "찜 성공";
    }

    public String deleteLike(Long postId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Post post = postRepository.findByPostIdAndMemberNot(postId, member).orElseThrow(
                () -> new CustomException(CANNOT_FIND_POST_NOT_EXIST)
        );
        likeRepository.deleteByPostAndMember(post, member);
        Long likeCnt = post.getPostLikeCnt();
        post.likeUpdate(likeCnt-1);
        return "찜 삭제";
    }
}
