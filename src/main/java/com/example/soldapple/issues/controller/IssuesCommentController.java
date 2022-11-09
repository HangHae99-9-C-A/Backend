package com.example.soldapple.issues.controller;

import com.example.soldapple.issues.dto.RequestDto.IssuesCommentRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesCommentResponseDto;
import com.example.soldapple.issues.service.IssuesCommentService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IssuesCommentController {
    private final IssuesCommentService issuesCommentService;

    @PostMapping("/issue-comment/{issuesId}")
    public List<IssuesCommentResponseDto> createIssuesComment(@PathVariable Long issuesId,
                                                              @RequestBody IssuesCommentRequestDto issuesCommentRequestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesCommentService.createIssuesComment(issuesId, issuesCommentRequestDto, userDetails);
    }
    @PutMapping("/issue-comment/{issuesCommentId}")
    public List<IssuesCommentResponseDto> updateIssuesComment(@PathVariable Long issuesCommentId,
                                                              @RequestBody IssuesCommentRequestDto issuesCommentRequestDto,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesCommentService.updateIssuesComment(issuesCommentId, issuesCommentRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/issue-comment/{issuesCommentId}")
    public List<IssuesCommentResponseDto> deleteIssuesComment(@PathVariable Long issuesCommentId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesCommentService.deleteIssuesComment(issuesCommentId, userDetails.getMember());
    }

}
