package com.example.soldapple.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatResDto {
    private String sender;
    private String message;
    private String sendDate;

    public ChatResDto(ChatReqDto chatReqDto, String sendDate){
        this.sender = chatReqDto.getSender();
        this.message = chatReqDto.getMessage();
        this.sendDate = sendDate;
    }
}
