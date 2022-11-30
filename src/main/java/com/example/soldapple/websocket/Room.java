package com.example.soldapple.websocket;

import com.example.soldapple.post.entity.Post;
import com.example.soldapple.security.user.UserDetailsImpl;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String title;

    private String state;

    @JsonManagedReference
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Chat> chats;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    private Long postUserId;
    private String postUserNickname;

    private Long joinUserId;
    private String joinUserNickname;

    public Room(Long postUserId, RoomReqDto roomReqDto, UserDetailsImpl userDetails, Post post){
        this.title = roomReqDto.getPostTitle();
        this.postUserId = postUserId;
        this.state = post.getCategory(); //상태 추가해야함
        this.postUserNickname = roomReqDto.getPostUserNickname();
        this.joinUserId = userDetails.getMember().getId();
        this.joinUserNickname = userDetails.getMember().getNickname();
        this.post = post;
    }
}
