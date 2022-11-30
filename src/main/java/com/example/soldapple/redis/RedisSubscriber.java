//package com.example.soldapple.redis;
//
//import com.example.soldapple.error.CustomException;
//import com.example.soldapple.websocket.ChatMessage;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class RedisSubscriber implements MessageListener {
//    private final ObjectMapper objectMapper;
//    private final RedisTemplate redisTemplate;
//    private final SimpMessageSendingOperations messageSendingOperations;
//
//    @Override
//    public void onMessage(Message message, byte[] pattern){
//        try{
//            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
//            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
//            messageSendingOperations.convertAndSend("/sub/chat/room/" + roomMessage.getChatRoom().getId(), roomMessage);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
//
//}
