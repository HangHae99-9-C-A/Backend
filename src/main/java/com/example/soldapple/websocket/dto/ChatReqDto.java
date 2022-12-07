package com.example.soldapple.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatReqDto {
    @NotNull
    private Long sender;    //memberId
    @NotNull
    private String message;
}
