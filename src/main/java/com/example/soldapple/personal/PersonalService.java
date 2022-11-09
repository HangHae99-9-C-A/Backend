package com.example.soldapple.personal;

import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = false) // default = false, but we need
@Service
public class PersonalService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //내 페이지 불러오기
    @Transactional(readOnly = true)
    public void getMyPage(Member member) {
        //회원 여부 체크
        Member myMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);
        List<Post> postList = postRepository.findAllByMember(member);
        postList.stream().map(PersonalResponseDto::new).collect(Collectors.toList());
        List<Comment> commentList = commentRepository.findAllByMember(member);
        commentList.stream().map(PersonalResponseDto::new).collect(Collectors.toList());
        Map<List<Post>,List<Comment>> listMap = new HashMap<>();
        listMap.put(postList,commentList);

       // return listMap;

    }

    //내 페이지 수정하기
    public void editMyPage(Member member) {
        List<Member> memberList = memberRepository.findAllById(member.getId());
        memberList.stream().map(PersonalResponseDto::new).collect(Collectors.toList());
    }

    //Response
    private static class PersonalResponseDto {
//       private List<Post> postList;
//       private List<Comment> commentList;
    }


}
