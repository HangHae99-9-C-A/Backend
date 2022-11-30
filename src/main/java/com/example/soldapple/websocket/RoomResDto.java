package com.example.soldapple.websocket;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoomResDto {
    private Long postId;
    private Long roomId;

    private String title;

    private Long price;

    private String state;
    private List<Chat> chatList;

    private LocalDateTime lastChatTime;

    private Long postUserId;
    private String postUserNickname;

    private Long joinUserId;
    private String joinUserNickname;


    public RoomResDto(Room room){

        this.postId = room.getPost().getPostId();
        this.roomId = room.getId();
        this.title = room.getTitle();
//        this.postImg = room.getPost().getImage().get(0).getImage();
        this.price = room.getPost().getUserPrice();
        this.chatList = room.getChats();
        this.state = room.getState();
//        this.lastCHatTime = room.getChats().get(chatList.size()).getSendDate(); 삼항연산자... 성우님
        this.postUserId = room.getPostUserId();
        this.postUserNickname = room.getPostUserNickname();
        this.joinUserId = room.getJoinUserId();
        this.joinUserNickname = room.getJoinUserNickname();

    }
}
