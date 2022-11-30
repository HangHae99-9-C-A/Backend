package com.example.soldapple.issues.controller;

import com.example.soldapple.issues.dto.RequestDto.IssuesCommentRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesCommentResponseDto;
import com.example.soldapple.issues.service.IssuesCommentService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue/comment")
public class IssuesCommentController {
    private final IssuesCommentService issuesCommentService;

    @PostMapping("/{issuesId}")
    public IssuesCommentResponseDto createIssuesComment(@PathVariable Long issuesId,
                                                              @RequestBody IssuesCommentRequestDto issuesCommentRequestDto,
                                                              @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesCommentService.createIssuesComment(issuesId, issuesCommentRequestDto, userDetails);
    }

    @PutMapping("/{issuesCommentId}")
    public IssuesCommentResponseDto updateIssuesComment(@PathVariable Long issuesCommentId,
                                                              @RequestBody IssuesCommentRequestDto issuesCommentRequestDto,
                                                              @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesCommentService.updateIssuesComment(issuesCommentId, issuesCommentRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/{issuesCommentId}")
    public String deleteIssuesComment(@PathVariable Long issuesCommentId,
                                                              @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesCommentService.deleteIssuesComment(issuesCommentId, userDetails.getMember());
    }
}
