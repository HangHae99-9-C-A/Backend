package com.example.soldapple.personal;

import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.repository.IssuesRepository;
import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.repository.PostRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = false) // default = false, but intuitive
@Service
public class PersonalService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final IssuesRepository issuesRepository;


    //내 페이지 불러오기
    @Transactional(readOnly = true)
    public PersonalResponseDto getMyPage(Member member) {
        //회원 여부 체크
        Member myMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);

        //get my (Post Comment Likes Issues) Lists

        // List<Post> postList = postRepository.findAllByMember(member);
        // List<Comment> commentList = commentRepository.findAllByMember(member);
        //List<Likes> myLikesList = postRepository.findAllByMember(member);
        List<Issues> issuesList = issuesRepository.findAllByMember(member);

        //response builder with Dto
        PersonalResponseDto personalResponseDto = PersonalResponseDto.builder()
                //   .myPostList(postList.stream().map(PostResponseDto::new).collect(Collectors.toList()))
                //  .myCommentList(commentList.stream().map(CommentResponseDto::new).collect(Collectors.toList()))
                // .myLikesList(myLikesList.stream().map(LikesResponseDto::new).collect(Collectors.toList()))
                .myIssuesList(issuesList.stream().map(IssuesResponseDto::new).collect(Collectors.toList()))
                .build();

        return personalResponseDto;

    }

    //내 페이지 수정하기
    public void editMyPage(Member member, MemberReqDto memberReqDto) {
        Member editMyMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);
        //editMyMember.
        editMyMember.update(memberReqDto);
        //memberResponse 필요합니다. 다른분 개발 부탁

    }

    //Response
    @Builder
    @Data
    static class PersonalResponseDto {
        // private List<PostResponseDto> myPostList;
        // private List<CommentResponseDto> myCommentList;
        // private List<PostResponseDto> myLikesList;
        private List<IssuesResponseDto> myIssuesList;
    }

}
