//package com.example.soldapple.websocket;
//
//import com.example.soldapple.security.user.UserDetailsImpl;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/chat")
//public class ChatRoomController {
//    private final ChatService chatService;
//
//    @ApiOperation(value = "room 전체 조회", notes = "채팅 룸 전체를 조회한다.")
//    @GetMapping("/rooms")
//    public List<ChatRoom> rooms() {
//        return chatService.findAllRoom();
//    }
//
//    @ApiOperation(value = "채팅방 개설", notes = "채팅방을 개설한다.")
//    @PostMapping("/room")
//    public ChatRoom createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Long userId1) {
//        return chatService.createChatRoom(userId1,userDetails.getMember().getId());
//    }
//
//    @ApiOperation(value = "방 정보 보기", notes = "방 정보")
//    @GetMapping("/room/{roomId}")
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        return chatService.findRoomById(roomId);
//    }
//
//    @ApiOperation(value = "customer 별 방 조회")
//    @GetMapping("/customer")
//    public List<ChatRoom> getRoomsByCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        return chatService.getCustomerEnterRooms(userDetails.getMember().getId());
//    }
//
//    @ApiOperation(value = "store 별 방 조회")
//    @GetMapping("/store")
//    public List<ChatRoom> getRoomsByStore(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        return chatService.getCustomerEnterRooms(userDetails.getMember().getId());
//    }
//}
