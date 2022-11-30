package com.example.soldapple.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomReqDto {
    private Long postId;
    private String postUserNickname;
    private String postTitle;
}
