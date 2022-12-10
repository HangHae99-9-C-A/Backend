package com.example.soldapple.post;


import com.example.soldapple.create_price.dto.GetIPhonePriceResDto;
import com.example.soldapple.create_price.dto.GetMacbookPriceResDto;
import com.example.soldapple.post.repository.CommentRepository;
import com.example.soldapple.post.requestdto.CommentReqDto;
import com.example.soldapple.post.requestdto.PostReqDto;
import com.example.soldapple.post.responsedto.CommentResponseDto;
import com.example.soldapple.post.responsedto.PostResponseDto;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    //무한스크롤 적용입니다
    // 모든 포스트 읽어오기
    @GetMapping("/all")
    public Page<PostResponseDto> getAllPost(Pageable pageable) {
        return postService.getAllPost(pageable);
    }

    // 모든 포스트 + 검색 읽어오기
    @GetMapping("/all/{search}")
    public Page<PostResponseDto> getAllPostWithSearch(Pageable pageable,
                                                      @PathVariable(name = "search", required = false) String search) {
        return postService.getAllPostWithSearch(pageable, search);
    }

    // 카테고리별 + 내 좋아요 한 포스트 읽어오기
    // api/post/{카테고리명}
    @GetMapping("category/{category}") // category = all
    public Page<?> getAllPostWithCategory(
            Pageable pageable, @PathVariable(name = "category", required = false) String category
    ) {
        return postService.getAllPostWithCategory(pageable, category);
    }

    // 카테고리별 + 검색어
    // api/post/{카테고리명}
    @GetMapping("category/{category}/{search}") // category = all
    public Page<?> getAllPostWithCategoryWithSearch(
            Pageable pageable,
            @PathVariable(name = "category", required = false) String category,
            @PathVariable(name = "search", required = false) String search) {
        return postService.getAllPostWithCategoryWithSearch(pageable, category, search);
    }

    //게시글 작성
    @PostMapping
    public PostResponseDto postCreate(@RequestPart(required = false) List<MultipartFile> multipartFiles,
                                      @RequestPart(required = false) PostReqDto postReqDto,
                                      @RequestPart(required = false) GetIPhonePriceResDto iphoneOption,
                                      @RequestPart(required = false) GetMacbookPriceResDto macbookOption,
                                      @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        String iphone = String.valueOf(iphoneOption);
        String post = String.valueOf(postReqDto);
        log.info("아이폰 옵션" + iphone);
        log.info("포스트 옵션" + post);
        return postService.postCreate(multipartFiles, postReqDto, iphoneOption, macbookOption, userDetails.getMember());
    }

    //게시글 하나 상세조회
    @GetMapping("/detail/{postId}")
    public PostResponseDto onePost(@PathVariable Long postId,
                                   @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.onePost(postId, userDetails.getMember());
    }

    //게시글 수정
    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(@RequestPart(required = false) List<MultipartFile> multipartFiles,
                                      @RequestPart(required = false) PostReqDto postReqDto,
                                      @PathVariable Long postId,
                                      @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.updatePost(multipartFiles, postId, postReqDto, userDetails.getMember());
    }

    //거래 완료
    @PatchMapping("/status/{postId}")
    public PostResponseDto updateStatus(@PathVariable Long postId,
                                        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updateStatus(postId, userDetails.getMember());
    }


    //게시글 삭제
    @DeleteMapping("/{postId}")
    public String postDelete(@PathVariable Long postId,
                             @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.postDelete(postId, userDetails.getMember());
    }

    /*게시글 댓글*/
    private final CommentRepository commentRepository;

    @PostMapping("/comment/{postId}") //댓글 작성
    public CommentResponseDto commentCreate(@PathVariable Long postId, @RequestBody @Valid CommentReqDto commentReqDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.commentCreate(postId, commentReqDto, userDetails.getMember());
    }

    @DeleteMapping("/comment/{commentId}") //댓글 삭제
    public String commentDelete(@PathVariable Long commentId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.commentDelete(commentId, userDetails.getMember());
    }

    @PutMapping("/comment/{commentId}") //댓글 수정
    public CommentResponseDto commentEdit(@PathVariable Long commentId, @RequestBody @Valid CommentReqDto commentReqDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.commentEdit(commentId, commentReqDto, userDetails.getMember());
    }
}