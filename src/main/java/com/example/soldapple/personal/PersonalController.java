package com.example.soldapple.personal;


import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;

    // 내 페이지 불러오기
    @GetMapping
    public PersonalService.PersonalResponseDto getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return personalService.getMyPage(userDetails.getMember());
    }

    // 내 페이지 변경
    @PatchMapping
    public void editMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody MemberReqDto memberReqDto) {
        personalService.editMyPage(userDetails.getMember(), memberReqDto);
    }

}
