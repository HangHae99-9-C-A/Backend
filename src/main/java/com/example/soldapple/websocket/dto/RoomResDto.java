package com.example.soldapple.websocket.dto;

import com.example.soldapple.post.entity.Image;
import com.example.soldapple.post.entity.SalesStatus;
import com.example.soldapple.websocket.entity.Chat;
import com.example.soldapple.websocket.entity.Room;
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
    private Image image;

    private Long price;

    private SalesStatus state;
    private List<Chat> chatList;

    private LocalDateTime lastChatTime;

    private Long postUserId;
    private String postUserNickname;

    private String postUserAvatarUrl;

    private Long joinUserId;
    private String joinUserNickname;

    private String joinUserAvatarUrl;

    private String nickname;

    private String otherNickname;

    public RoomResDto(Room room, String nickname, String otherNickname){
        this.postId = room.getPost().getPostId();
        this.roomId = room.getId();
        this.title = room.getPost().getTitle();
        this.image = room.getPost().getImages().get(0);
        this.price = room.getPost().getUserPrice();
        this.chatList = room.getChats();
        this.state = room.getPost().getSalesStatus();
        this.postUserId = room.getPost().getMember().getId();
        this.postUserNickname = room.getPost().getMember().getNickname();
        this.postUserAvatarUrl = room.getPost().getMember().getAvatarUrl();
        this.joinUserId = room.getJoinUserId();
        this.joinUserNickname = room.getJoinUserNickname();
        this.joinUserAvatarUrl = room.getJoinUserAvatarUrl();
        this.nickname = nickname;
        this.otherNickname = otherNickname;
    }
}
