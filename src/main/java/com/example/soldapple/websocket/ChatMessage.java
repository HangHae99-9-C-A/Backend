//package com.example.soldapple.websocket;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class ChatMessage {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private MessageType type;
//    private String sender;
//    private String message;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "chatroom_id")
//    private ChatRoom chatRoom;
//
//    public static ChatMessage createChatMessage(ChatRoom chatRoom, String sender, String message, MessageType type){
//        ChatMessage chatMessage = ChatMessage.builder()
//                .chatRoom(chatRoom)
//                .sender(sender)
//                .message(message)
//                .type(type)
//                .build();
//        return chatMessage;
//    }
//
//    public void setSender(String sender) {
//        this.sender = sender;
//    }
//
//    public void setMessage(String message){
//        this.message = message;
//    }
//
//    public enum MessageType {
//        ENTER, QUIT, TALK
//    }
//}
