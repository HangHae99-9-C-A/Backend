package com.example.soldapple.websocket.entity;

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

    @JsonManagedReference   //순환참조를 막기위해 사용
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Chat> chats;

    @JsonBackReference  //순환참조를 막기위해 사용
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;


    private Long joinUserId;
    private String joinUserNickname;

    private String joinUserAvatarUrl;

    public Room(UserDetailsImpl userDetails, Post post){
        this.joinUserId = userDetails.getMember().getId();
        this.joinUserNickname = userDetails.getMember().getNickname();
        this.joinUserAvatarUrl = userDetails.getMember().getAvatarUrl();
        this.post = post;
    }
}
