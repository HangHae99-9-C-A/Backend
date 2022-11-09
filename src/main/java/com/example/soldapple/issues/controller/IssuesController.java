package com.example.soldapple.issues.controller;

import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.service.IssuesService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class IssuesController {
    private final IssuesService issuesService;

    @PostMapping("/issue")
    public List<IssuesResponseDto> createIssue(@RequestBody IssuesRequestDto issuesRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesService.createIssue(issuesRequestDto, userDetails.getMember());
    }

    @GetMapping("/issues")
    public List<IssuesResponseDto> allIssues(){return issuesService.allIssues();}

    @GetMapping("/issue/{issuesId}")
    public IssuesResponseDto oneIssue(@PathVariable Long issuesId){
        return issuesService.oneIssue(issuesId);
    }

    @DeleteMapping("/issue/{issuesId}")
    public List<IssuesResponseDto> deleteIssue(@PathVariable Long issuesId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesService.deleteIssue(issuesId, userDetails);
    }
}
