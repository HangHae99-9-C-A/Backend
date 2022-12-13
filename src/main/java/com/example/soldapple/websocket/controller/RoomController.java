package com.example.soldapple.websocket.controller;

import com.example.soldapple.security.user.UserDetailsImpl;
import com.example.soldapple.websocket.ChattingService;
import com.example.soldapple.websocket.dto.ChatSelectReqDto;
import com.example.soldapple.websocket.dto.RoomReqDto;
import com.example.soldapple.websocket.dto.RoomResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {
    private final ChattingService chattingService;

    @PostMapping("/roomInfo")   //방에 입장 //api주소 바꿔야할듯
    public RoomResDto joinRoom(@RequestBody ChatSelectReqDto chatSelectReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request){
        HttpSession sessions = request.getSession();
        return chattingService.joinRoom(chatSelectReqDto,userDetails);
    }

    @PostMapping("/room")   //방 생성
    public RoomResDto createRoom(@RequestBody RoomReqDto roomReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return chattingService.createRoom(roomReqDto, userDetails);
    }

    @GetMapping("/roomList")    //참가한 방 리스트 보기
    public List<RoomResDto> roomList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return chattingService.roomList(userDetails);
    }
}
