package com.example.soldapple.issues.controller;

import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.service.IssuesService;
import com.example.soldapple.post.dto.PostReqDto;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssuesController {
    private final IssuesService issuesService;

    @PostMapping("")
    public IssuesResponseDto createIssue(@RequestPart List<MultipartFile> multipartFiles,
                                         @RequestPart IssuesRequestDto issuesRequestDto,
                                         @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) throws IOException {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.createIssue(multipartFiles, issuesRequestDto, userDetails.getMember());
    }

    @GetMapping("")
    public List<IssuesResponseDto> allIssues(@AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails){
        return issuesService.allIssues(userDetails.getMember());
    }

    @GetMapping("/{issuesId}")
    public IssuesResponseDto oneIssue(@PathVariable Long issuesId){
        return issuesService.oneIssue(issuesId);
    }

    @DeleteMapping("/{issuesId}")
    public String deleteIssue(@PathVariable Long issuesId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesService.deleteIssue(issuesId, userDetails);
    }
}