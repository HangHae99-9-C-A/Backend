package com.example.soldapple.websocket;

import com.example.soldapple.post.entity.Image;
import com.example.soldapple.post.entity.SalesStatus;
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
//        this.postImg = room.getPost().getImage().get(0).getImage();
        this.price = room.getPost().getUserPrice();
        this.chatList = room.getChats();
        this.state = room.getPost().getSalesStatus();
//        this.lastCHatTime = room.getChats().get(chatList.size()).getSendDate(); 삼항연산자... 성우님
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
