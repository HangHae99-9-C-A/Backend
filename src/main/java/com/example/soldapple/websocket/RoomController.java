package com.example.soldapple.websocket;

import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {
    private final ChatService chatService;
    private final RoomService roomService;

    @PostMapping("/roomInfo")
    public RoomResDto joinRoom(@RequestBody ChatSelectReqDto chatSelectReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomService.joinRoom(chatSelectReqDto,userDetails);
    }

    @PostMapping("/room")
    public RoomResDto createRoom(@RequestBody RoomReqDto roomReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomService.createRoom(roomReqDto, userDetails);
    }

    @GetMapping("/roomList")
    public List<RoomResDto> roomList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return roomService.roomList(userDetails);
    }
}
