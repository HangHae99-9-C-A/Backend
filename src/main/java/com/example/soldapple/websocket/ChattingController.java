package com.example.soldapple.websocket;

//import com.example.soldapple.member.entity.Member;
//import com.example.soldapple.redis.RedisPublisher;
//import com.example.soldapple.security.user.UserDetailsImpl;
//import io.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.security.access.method.P;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin
public class ChattingController {
    private final SimpMessagingTemplate template;
    private final ChatService chatService;
//    private final RedisPublisher redisPublisher;
//    private final ChatService chatService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
//    @ApiOperation(value = "채팅방 메시지", notes = "메시지")
//    @MessageMapping("/chat/message")
//    @SendTo("/sub/chat")
//    public void message(ChatMessageDto message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Member member = userDetails.getMember();
//        // 로그인 회원 정보로 대화명 설정
//        ChatRoom chatRoom=chatService.findRoomById(message.getRoomId());
//        ChatMessage message1=ChatMessage.createChatMessage(chatRoom, member.getNickname(), message.getMessage(), message.getType());
//        // 채팅방 입장시에는 대화명과 메시지를 자동으로 세팅한다.
//        log.info("채팅 메시지");
//        if (ChatMessage.MessageType.ENTER.equals(message1.getType())) {
//            message1.setSender("[알림]");
//            message1.setMessage(member.getNickname() + "님이 입장하셨습니다.");
//        }else if(ChatMessage.MessageType.QUIT.equals(message1.getType())){
//            message1.setSender("[알림]");
//            message1.setMessage(member.getNickname() + "님이 퇴장하셨습니다.");
//            chatService.deleteById(message1.getChatRoom());
//        }
//        chatRoom.addChatMessages(message1);
//        // Websocket에 발행된 메시지를 redis로 발행(publish)
//        redisPublisher.publish(chatService.getTopic(message.getRoomId()), message1);
//    }

//    @MessageMapping(value = "1")
//    @SendTo("/sub/1")
//    public ChatMessageDto chat(MessageDto messageDto) throws Exception{
//        Thread.sleep(500);
//        return new ChatMessageDto(messageDto.getMessage());
//    }
    @MessageMapping(value = "1")
    @SendTo("/sub/1")
    public void message(@Valid ChatReqDto message) throws MessagingException {
        Chat chat = chatService.createChat(1L,message);

        ChatResDto chatResDto = new ChatResDto(message,chat.getSendDate());
        template.convertAndSend("/sub/"+1L,chatResDto);
    }
}
