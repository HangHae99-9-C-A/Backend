package com.example.soldapple.websocket;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatResDto {
    private String sender;
    private String message;
    private LocalDateTime createdAt;

    public ChatResDto(ChatReqDto chatReqDto, LocalDateTime localDateTime){
        this.sender = chatReqDto.getSender();
        this.message = chatReqDto.getMessage();
        this.createdAt = localDateTime;
    }
}
