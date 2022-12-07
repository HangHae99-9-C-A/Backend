package com.example.soldapple.websocket;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.error.ErrorCode;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.security.user.UserDetailsImpl;
import com.example.soldapple.websocket.dto.ChatReqDto;
import com.example.soldapple.websocket.dto.ChatSelectReqDto;
import com.example.soldapple.websocket.dto.RoomReqDto;
import com.example.soldapple.websocket.dto.RoomResDto;
import com.example.soldapple.websocket.entity.Chat;
import com.example.soldapple.websocket.entity.Room;
import com.example.soldapple.websocket.repository.ChatRepository;
import com.example.soldapple.websocket.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChattingService {
    private final RoomRepository roomRepository;
    private final PostRepository postRepository;
    private final ChatRepository chatRepository;
    String otherNickname;
    String otherUserAvatarUrl;
    Long senderId;

    public RoomResDto joinRoom(ChatSelectReqDto chatSelectReqDto, UserDetailsImpl userDetails) {    //방 입장
        Room room = roomRepository.findRoomByIdAndPostMemberId(chatSelectReqDto.getRoomId(), userDetails.getMember().getId())   //게시글 작성자와 member가 일치하면 방 객체 가져옴
                .orElseGet( () ->roomRepository.findRoomByIdAndJoinUserId(chatSelectReqDto.getRoomId(), userDetails.getMember().getId()).orElseThrow(   //게시글 작성자와 member가 일치하지 않을 경우 참가자와 일치하면 방 객체 가져옴
                                () -> new CustomException(ErrorCode.CANNOT_FOUND_CHATROOM)
                        ));
        senderId = userDetails.getMember().getId();
        if(senderId.equals(room.getJoinUserId())){   //상대방 닉네임을 저장함
            otherNickname = room.getPost().getMember().getNickname();
            otherUserAvatarUrl = room.getPost().getMember().getAvatarUrl();
        }else{
            otherNickname = room.getJoinUserNickname();
            otherUserAvatarUrl = room.getJoinUserAvatarUrl();
        }
        return new RoomResDto(room, userDetails.getMember().getNickname(),otherNickname, otherUserAvatarUrl, senderId);

    }

    public RoomResDto createRoom(RoomReqDto roomReqDto, UserDetailsImpl userDetails) {  //방 생성
        Post post = postRepository.findById(roomReqDto.getPostId()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_POST_ON_CHAT)
        );
        if(post.getMember().getId().equals(userDetails.getMember().getId())){   //자기 자신의 게시글에서는 방생성 불가
            throw new CustomException(ErrorCode.CANNOT_MAKE_ROOM_ALONE);
        }
        Room room = roomRepository.findRoomByJoinUserIdAndPostMemberIdAndPostPostId(userDetails.getMember().getId(), post.getMember().getId(), roomReqDto.getPostId())  //이미 만든방이 있다면 방 객체 가져옴
                .orElseGet(() ->new Room(userDetails, post));   //만들어진 방이 없다면 방 객체 새로 생성

        senderId = userDetails.getMember().getId();

        if(senderId.equals(room.getJoinUserId())){   //상대방 닉네임을 저장함
            otherNickname = room.getPost().getMember().getNickname();
            otherUserAvatarUrl = room.getPost().getMember().getAvatarUrl();
        }else{
            otherNickname = room.getJoinUserNickname();
            otherUserAvatarUrl = room.getJoinUserAvatarUrl();
        }

        roomRepository.save(room);
        return new RoomResDto(room, userDetails.getMember().getNickname(),otherNickname, otherUserAvatarUrl, senderId);
    }

    public List<RoomResDto> roomList(UserDetailsImpl userDetails) { //채팅방 리스트 가져옴
        List<Room> roomList = roomRepository.findAllByJoinUserIdOrPostMemberIdOrderByIdDesc(userDetails.getMember().getId(), userDetails.getMember().getId());

        List<RoomResDto> roomResDtos = new ArrayList<>();
        senderId = userDetails.getMember().getId();

        for (Room room : roomList) {    //전체 체팅방 리스트 생성
            if(senderId.equals(room.getJoinUserId())){   //상대방 닉네임을 저장함
                otherNickname = room.getPost().getMember().getNickname();
                otherUserAvatarUrl = room.getPost().getMember().getAvatarUrl();
            }else{
                otherNickname = room.getJoinUserNickname();
                otherUserAvatarUrl = room.getJoinUserAvatarUrl();
            }
            roomResDtos.add(new RoomResDto(room, userDetails.getMember().getNickname(),otherNickname, otherUserAvatarUrl, senderId));
        }

        return roomResDtos;
    }

    public Chat createChat(Long roomId, ChatReqDto chatReqDto, String sendDate){ //채팅 저장
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.CANNOT_FOUND_CHATROOM)
        );
        Chat chat = Chat.builder()
                .room(room)
                .sender(chatReqDto.getSender())
                .message(chatReqDto.getMessage())
                .sendDate(sendDate)
                .build();
        chatRepository.save(chat);
        return chat;
    }
}
