package com.example.soldapple.websocket;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String sendDate;

    @Builder
    public Chat(Room room, String sender, String message, String sendDate){
        this.room = room;
        this.sender = sender;
        this.message = message;
        this.sendDate = sendDate;
    }

    public static Chat createChat(Room room, String sender, String message,String sendDate){
        return Chat.builder()
                .room(room)
                .sender(sender)
                .message(message)
                .sendDate(sendDate)
                .build();
    }
}
