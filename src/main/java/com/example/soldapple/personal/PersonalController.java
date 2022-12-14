package com.example.soldapple.personal;


import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    // 내 페이지 불러오기
    @GetMapping
    public MemberResponseDto getMyInfo(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyInfo(userDetails.getMember());
    }

    //내가 쓴 포스트 리스트
    @GetMapping("/posts")
    public PersonalService.PersonalResponseDto getMyPost(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyPost(userDetails.getMember());
    }

    // 내가 쓴 커멘트 리스트
    @GetMapping("/comments")
    public PersonalService.PersonalResponseDto getMyComment(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyComment(userDetails.getMember());
    }

    // 내가 쓴 이슈글 리스트
    @GetMapping("/issues")
    public PersonalService.PersonalResponseDto getMyIssue(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyIssue(userDetails.getMember());
    }

    //내가 좋아요 한 포스트 리스트
    @GetMapping("/likes")
    public PersonalService.PersonalResponseDto getMyLikes(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyLikes(userDetails.getMember());
    }

    // 내 페이지 변경
    @PatchMapping
    public void editMyPage(@RequestPart(name = "myInfoRequestDto", required = false) MyInfoRequestDto myInfoRequestDto,
                           @RequestPart(name = "image", required = false) MultipartFile image,
                           @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        personalService.editMyPage(myInfoRequestDto, userDetails.getMember(), image);

    }

    // 판매자 상세 정보 불러오기
    @GetMapping("/seller/{memberId}")
    public PersonalService.PersonalResponseDto getUserInfo(@PathVariable("memberId") Long memberId) {
        return personalService.getUserInfo(memberId);
    }
}
