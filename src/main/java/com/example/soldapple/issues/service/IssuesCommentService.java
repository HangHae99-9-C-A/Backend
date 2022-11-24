package com.example.soldapple.issues.service;

import com.example.soldapple.issues.dto.RequestDto.IssuesCommentRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesCommentResponseDto;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.entity.IssuesComment;
import com.example.soldapple.issues.repository.IssuesCommentRepository;
import com.example.soldapple.issues.repository.IssuesRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IssuesCommentService {
    private final IssuesRepository issuesRepository;
    private final IssuesCommentRepository issuesCommentRepository;
    //이의제기 댓글 작성
    public IssuesCommentResponseDto createIssuesComment(Long issuesId, IssuesCommentRequestDto issuesCommentRequestDto, UserDetailsImpl userDetails) {
        Issues issues = issuesRepository.findByIssuesId(issuesId).orElseThrow(
                ()-> new IllegalArgumentException("해당 이의제기 글이 존재하지 않습니다.")
        );
        IssuesComment issuesComment = new IssuesComment(issues, userDetails.getMember(),  issuesCommentRequestDto.getIssuesComment());
        issuesCommentRepository.save(issuesComment);
        String avatarUrl;
        if(issuesComment.getMember().getAvatarUrl()==null) {
            avatarUrl = "https://s3.ap-northeast-2.amazonaws.com/myawsbucket.refined-stone/default/photoimg.png";
        } else{
            avatarUrl = issuesComment.getMember().getAvatarUrl();
        }
        return new IssuesCommentResponseDto(issuesComment, avatarUrl);
    }

    //이의제기 댓글 삭제
    public String deleteIssuesComment(Long issuesCommentId, Member member) {

        IssuesComment issuesComment = issuesCommentRepository.findByIssuesCommentIdAndMember(issuesCommentId, member).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없거나 삭제 권한이 없습니다.")
        );
        issuesCommentRepository.delete(issuesComment);
        return "댓글 삭제 성공";

    }

    //이의제기 댓글 수정
    public IssuesCommentResponseDto updateIssuesComment(Long issuesCommentId, IssuesCommentRequestDto issuesCommentRequestDto, Member member) {
        IssuesComment issuesComment =issuesCommentRepository.findByIssuesCommentIdAndMember(issuesCommentId, member).orElseThrow(
                ()->new RuntimeException("해당 댓글이 없거나 수정 권한이 없습니다.")
        );
        issuesComment.setIssuesComment(issuesCommentRequestDto.getIssuesComment());
        issuesCommentRepository.save(issuesComment);
        String avatarUrl;
        if(issuesComment.getMember().getAvatarUrl()==null) {
            avatarUrl = "https://s3.ap-northeast-2.amazonaws.com/myawsbucket.refined-stone/default/photoimg.png";
        } else{
            avatarUrl = issuesComment.getMember().getAvatarUrl();
        }
        return new IssuesCommentResponseDto(issuesComment, avatarUrl);
    }
}
