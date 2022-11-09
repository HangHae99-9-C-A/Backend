package com.example.soldapple.issues.service;

import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.entity.Issues;
import com.example.soldapple.issues.repository.IssuesRepository;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssuesService {
    private final IssuesRepository issuesRepository;

    //이의제기글 작성
    @Transactional
    public List<IssuesResponseDto> createIssue(IssuesRequestDto issuesRequestDto, Member member) {
        Issues issues = new Issues(issuesRequestDto, member);
        issuesRepository.save(issues);
        //response로 전체 조회값 돌려줌
        return allIssues();
    }

    //이의제기글 전체 조회
    public List<IssuesResponseDto> allIssues() {
        List<Issues> issues = issuesRepository.findAllByOrderByCreatedAtDesc();
        List<IssuesResponseDto> issuesResponseDtos = new ArrayList<IssuesResponseDto>();
        for (Issues issue : issues) {
            issuesResponseDtos.add(new IssuesResponseDto(issue));
        }
        return issuesResponseDtos;
    }

    //이의제기글 하나 조회
    public IssuesResponseDto oneIssue(Long issuesId) {
        Issues issue = issuesRepository.findByIssuesId(issuesId).orElseThrow(
                () -> new IllegalArgumentException("해당 이의제기 글이 존재하지 않습니다.")
        );
        return new IssuesResponseDto(issue);
    }

    //이의제기글 삭제
    @Transactional
    public List<IssuesResponseDto> deleteIssue(Long issuesId, UserDetailsImpl userDetails) {
        Issues issues = issuesRepository.findByIssuesIdAndMember(issuesId, userDetails.getMember()).orElseThrow(
                ()->new IllegalArgumentException("해당 이의제기 글이 존재하지 않거나 삭제 권한이 없습니다.")
        );
        issuesRepository.deleteById(issues.getIssuesId());
        return allIssues();
    }
}
