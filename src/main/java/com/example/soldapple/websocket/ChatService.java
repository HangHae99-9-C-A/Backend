package com.example.soldapple.websocket;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public Chat createChat(Long roomId, ChatReqDto chatReqDto){
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.CANNOT_FIND_POST_NOT_EXIST) //예외처리해야함
        );
        Chat chat = Chat.builder()
                .room(room)
                .sender(chatReqDto.getSender())
                .message(chatReqDto.getMessage())
                .build();
        chatRepository.save(chat);
        return chat;
    }

    public List<Chat> findAllChatByRoomId(Long roomId){
        return chatRepository.findByRoom_PostPostId(roomId);
    }
}
