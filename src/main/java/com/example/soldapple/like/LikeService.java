package com.example.soldapple.like;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.repository.IssuesRepository;
import com.example.soldapple.like.entity.IssuesLike;
import com.example.soldapple.like.entity.Like;
import com.example.soldapple.like.repository.IssuesLikeRepository;
import com.example.soldapple.like.repository.LikeRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.soldapple.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    //게시글 좋아요 생성
    public String addLike(Long postId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Post post = postRepository.findByPostIdAndMemberNot(postId, member).orElseThrow(
                () -> new CustomException(CANNOT_LIKE_BY_POST_WRITER)
        );

        Long likeCnt = post.getPostLikeCnt();
        Like like = new Like(post, member);
        post.likeUpdate(likeCnt+1);
        likeRepository.save(like);
        return "찜 성공";
    }

    //게시글 좋아요 삭제
    public String deleteLike(Long postId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Post post = postRepository.findByPostIdAndMemberNot(postId, member).orElseThrow(
                () -> new CustomException(CANNOT_DISLIKE_BY_POST_WRITER)
        );
        likeRepository.deleteByPostAndMember(post, member);
        Long likeCnt = post.getPostLikeCnt();
        post.likeUpdate(likeCnt-1);
        return "찜 삭제";
    }


    private final IssuesRepository issuesRepository;
    private final IssuesLikeRepository issueslikeRepository;

    //이의제기 좋아요 생성
    public String addIssueLike(Long issuesId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Issues issues = issuesRepository.findByIssuesIdAndMemberNot(issuesId,member).orElseThrow(
                () -> new CustomException(CANNOT_LIKE_BY_POST_WRITER)
        );
        Long likeCnt = issues.getIssuesLikeCnt();
        IssuesLike issueslike = new IssuesLike(issues, member);
        issues.likeUpdate(likeCnt+1);
        issueslikeRepository.save(issueslike);
        return "찜 성공";
    }

    //이의제기 좋아요 삭제
    public String deleteIssueLike(Long issuesId, UserDetailsImpl userDetails){
        Member member = memberRepository.findById(userDetails.getMember().getId()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        Issues issues = issuesRepository.findByIssuesIdAndMemberNot(issuesId,member).orElseThrow(
                () -> new CustomException(CANNOT_DISLIKE_BY_POST_WRITER)
        );
        issueslikeRepository.deleteByIssuesAndMember(issues, member);
        Long likeCnt = issues.getIssuesLikeCnt();
        issues.likeUpdate(likeCnt-1);
        return "찜 삭제";
    }
}
