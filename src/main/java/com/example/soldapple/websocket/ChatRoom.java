//package com.example.soldapple.websocket;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//public class ChatRoom implements Serializable {
//    @Id
//    @Column(name = "chatroom_id")
//    private String id;
//
//    private static final long serialVersionUID = 6494678977089006639L;
//    private String name;
//    private Long userId1;
//    private Long userId2;
//
//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
//    private List<ChatMessage> chatMessages = new ArrayList<>();
//
//    public static ChatRoom create(String name, Long userId1, Long userId2){
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.id = UUID.randomUUID().toString();
//        chatRoom.name = name;
//        chatRoom.userId1 = userId1;
//        chatRoom.userId2 = userId2;
//        return chatRoom;
//    }
//
//    public void addChatMessages(ChatMessage chatMessage){
//        this.chatMessages.add(chatMessage);
//    }
//}
