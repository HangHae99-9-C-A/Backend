package com.example.soldapple.personal;


import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@RestController
@RequestMapping("/myinfo")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    // 내 페이지 불러오기
    @GetMapping
    public MemberResponseDto getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyInfo(userDetails.getMember());
    }

    @GetMapping("/post")
    public PersonalService.PersonalResponseDto getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyPost(userDetails.getMember());
    }

    @GetMapping("/comment")
    public PersonalService.PersonalResponseDto getMyComment(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyComment(userDetails.getMember());
    }

    @GetMapping("/issue")
    public PersonalService.PersonalResponseDto getMyIssue(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyIssue(userDetails.getMember());
    }

//    @GetMapping("/likes")
//    public PersonalService.PersonalResponseDto getMyLikes(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return personalService.getMyLikes(userDetails.getMember());
//    }

    // 내 페이지 변경
    @PatchMapping("/edit")
    public void editMyPage(@RequestPart(name = "myInfoRequestDto", required = false) MyInfoRequestDto myInfoRequestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           MultipartHttpServletRequest img) throws IOException {

        MultipartFile imgFile = img.getFile("image");
        personalService.editMyPage(myInfoRequestDto, userDetails.getMember(), imgFile);

    }

    // 판매자 상세 정보 불러오기
    @GetMapping("/seller/{nickname}")
    public PersonalService.PersonalResponseDto getUserInfo(@PathVariable("nickname") String nickname) {
        return personalService.getUserInfo(nickname);
    }
}
