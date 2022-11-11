package com.example.soldapple.personal;


import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/myinfo")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    // 내 페이지 불러오기
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
    public void editMyPage(@RequestPart(name = "memberReqDto", required = false) MemberReqDto memberReqDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                           MultipartHttpServletRequest img) throws IOException {

        MultipartFile imgFile = img.getFile("image");
        personalService.editMyPage(memberReqDto,userDetails.getMember(),imgFile);

    }

}
