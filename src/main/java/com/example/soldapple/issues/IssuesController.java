package com.example.soldapple.issues;

import com.example.soldapple.create_price.dto.GetIPhonePriceResDto;
import com.example.soldapple.create_price.dto.GetMacbookPriceResDto;
import com.example.soldapple.issues.dto.RequestDto.IssuesCommentRequestDto;
import com.example.soldapple.issues.dto.RequestDto.IssuesRequestDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesCommentResponseDto;
import com.example.soldapple.issues.dto.ResponseDto.IssuesResponseDto;
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
@RequestMapping("/issues")
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

    @PostMapping //이의제기 작성
    public IssuesResponseDto createIssue(@RequestPart(required = false) List<MultipartFile> multipartFiles,
                                         @RequestPart(required = false) IssuesRequestDto issuesRequestDto,
                                         @RequestPart(required = false) GetIPhonePriceResDto iphoneOption,
                                         @RequestPart(required = false) GetMacbookPriceResDto macbookOption,
                                         @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) throws IOException {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.createIssue(multipartFiles, issuesRequestDto, iphoneOption, macbookOption, userDetails.getMember());
    }

    @GetMapping("/detail/{issuesId}") //이의제기 하나 상세조회
    public IssuesResponseDto oneIssue(@PathVariable Long issuesId,
                                      @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.oneIssue(issuesId, userDetails.getMember());
    }

    @PatchMapping("/{issuesId}") //이의제기 수정
    public IssuesResponseDto updateIssue(@PathVariable Long issuesId,
                                         @RequestPart IssuesRequestDto issuesRequestDto,
                                         @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.updateIssue(issuesId, issuesRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/{issuesId}") //이의제기 삭제
    public String deleteIssue(@PathVariable Long issuesId,
                              @AuthenticationPrincipal @ApiIgnore UserDetailsImpl userDetails) {
        System.out.println("==========컨트롤러 지나는중==========");
        return issuesService.deleteIssue(issuesId, userDetails.getMember());
    }

    /*이의제기 댓글*/
    private final IssuesService issuesCommentService;

    @PostMapping("/comment/{issuesId}") //댓글 작성
    public IssuesCommentResponseDto createIssuesComment(@PathVariable Long issuesId,
                                                        @RequestBody IssuesCommentRequestDto issuesCommentRequestDto,
                                                        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesService.createIssuesComment(issuesId, issuesCommentRequestDto, userDetails.getMember());
    }

    @PutMapping("/comment/{issuesCommentId}") //댓글 수정
    public IssuesCommentResponseDto updateIssuesComment(@PathVariable Long issuesCommentId,
                                                        @RequestBody IssuesCommentRequestDto issuesCommentRequestDto,
                                                        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesService.updateIssuesComment(issuesCommentId, issuesCommentRequestDto, userDetails.getMember());
    }

    @DeleteMapping("/comment/{issuesCommentId}") //댓글 삭제
    public String deleteIssuesComment(@PathVariable Long issuesCommentId,
                                      @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        return issuesService.deleteIssuesComment(issuesCommentId, userDetails.getMember());
    }
}