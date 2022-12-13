package com.example.soldapple.websocket.controller;

import com.example.soldapple.websocket.ChattingService;
import com.example.soldapple.websocket.dto.ChatReqDto;
import com.example.soldapple.websocket.dto.ChatResDto;
import com.example.soldapple.websocket.entity.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
@CrossOrigin
public class ChattingController {
//    HttpServletRequest request;
    private final SimpMessagingTemplate template;
    private final ChattingService chattingService;

    @MessageMapping(value = "{roomId}")
    @SendTo("/sub/{roomId}")
    public void message(@DestinationVariable Long roomId, @Valid ChatReqDto message) throws MessagingException {
//        HttpSession sessions = request.getSession();
//        Long memberId = (Long) sessions.getAttribute(accessor.getSessionId());
        String sendDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")); //채팅 작성된 시간의 형식을 변경
        Chat chat = chattingService.createChat(roomId,message,sendDate);    //채팅 저장

        ChatResDto chatResDto = new ChatResDto(message,chat.getSendDate()); //채팅 보낸이, 메세지, 작성시간을 저장
        template.convertAndSend("/sub/"+roomId,chatResDto);   //구독된 방에 채팅 보낸이, 메세지, 작성시간을 뿌려줌
    }

//    @EventListener(SessionConnectEvent.class)
//    public void onConnect(SessionConnectEvent event){
//        HttpSession sessions = request.getSession();
//
//        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
//        String memberId = event.getMessage().getHeaders().get("nativeHeaders").toString().split("MemberId=\\[")[1].split("]")[0];
//
//        sessions.setAttribute(sessionId, Long.valueOf(memberId));
//    }
//
//    @EventListener(SessionDisconnectEvent.class)
//    public void onDisconnect(SessionDisconnectEvent event){
//        HttpSession sessions = request.getSession();
//
//        sessions.removeAttribute(event.getSessionId());
//    }
}
