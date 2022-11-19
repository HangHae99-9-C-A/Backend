package com.example.soldapple.issues.controller;

import com.example.soldapple.create_price.dto.GetIPhonePriceResDto;
import com.example.soldapple.create_price.dto.GetMacbookPriceResDto;
import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
import com.example.soldapple.issues.service.IssuesService;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issue")
public class IssuesController {
    private final IssuesService issuesService;

    //무한스크롤 적용입니다
    // 모든 이의제기글 읽어오기
    @GetMapping("/all")
    public Page<IssuesResponseDto> getAllIssues(Pageable pageable) {
        return issuesService.getAllIssues(pageable);

    }

    // 모든 이의제기글 읽어오기
    @GetMapping("/all/{search}")
    public Page<IssuesResponseDto> getAllIssuesWithSearch(Pageable pageable,
                                                          @PathVariable(name = "search", required = false) String search) {
        return issuesService.getAllIssuesWithSearch(pageable, search);

    }

    // 카테고리별 + 내 좋아요 한 포스트 읽어오기
    // api/post/{카테고리명}
    @GetMapping("category/{category}")
    public Page<IssuesResponseDto> getAllPostWithCategory(
            Pageable pageable, @PathVariable(name = "category") String category) {
        return issuesService.getAllIssuesWithCategory(pageable, category);

    }

    // 카테고리 + 검색 + sort
    @GetMapping("category/{category}/{search}") // category = all
    public Page<?> getAllIssuesWithCategoryWithSearch(
            Pageable pageable,
            @PathVariable(name = "category") String category,
            @PathVariable(name = "search") String search) {
        return issuesService.getAllIssuesWithCategoryWithSearch(pageable, category, search);
    }

    @PostMapping
    public IssuesResponseDto createIssue(@RequestPart(required = false) List<MultipartFile> multipartFiles,
                                         @RequestPart(required = false) IssuesRequestDto issuesRequestDto,
                                         @RequestPart(required = false) GetIPhonePriceResDto iphoneOption,
                                         @RequestPart(required = false) GetMacbookPriceResDto macbookOption,
                                         @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) throws IOException {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.createIssue(multipartFiles, issuesRequestDto, iphoneOption, macbookOption, userDetails.getMember());
    }

//    @GetMapping
//    public List<IssuesResponseDto> allIssues(@AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) {
//        System.out.println("==========컨트롤러 지나는중==========");
//        return issuesService.allIssues(userDetails.getMember());
//    }

    @GetMapping("/detail/{issuesId}")
    public IssuesResponseDto oneIssue(@PathVariable Long issuesId,
                                      @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.oneIssue(issuesId, userDetails.getMember());
    }

    @PatchMapping("/{issuesId}")
    public IssuesResponseDto updateIssue(@PathVariable Long issuesId,
                                         @RequestPart IssuesRequestDto issuesRequestDto,
                                         @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.updateIssue(issuesId, issuesRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/{issuesId}")
    public String deleteIssue(@PathVariable Long issuesId,
                              @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.deleteIssue(issuesId, userDetails.getMember());
    }
}