package com.example.soldapple.websocket;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.error.ErrorCode;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingService {
    private final RoomRepository roomRepository;
    private final PostRepository postRepository;
    private final ChatRepository chatRepository;

    public RoomResDto joinRoom(ChatSelectReqDto chatSelectReqDto, UserDetailsImpl userDetails) {    //방 입장
        Room room = roomRepository.findRoomByIdAndPostMemberId(chatSelectReqDto.getRoomId(), userDetails.getMember().getId())   //게시글 작성자와 member가 일치하면 방 객체 가져옴
                .orElseGet( () ->roomRepository.findRoomByIdAndJoinUserId(chatSelectReqDto.getRoomId(), userDetails.getMember().getId()).orElseThrow(   //게시글 작성자와 member가 일치하지 않을 경우 참가자와 일치하면 방 객체 가져옴
                                () -> new CustomException(ErrorCode.CANNOT_FIND_POST_NOT_EXIST) // 예외처리 멘트 고쳐야함
                        ));
        String otherNickname;
        if(userDetails.getMember().getId().equals(room.getJoinUserId())){   //상대방 닉네임을 저장함
            otherNickname = room.getPost().getMember().getNickname();
        }else{
            otherNickname = room.getJoinUserNickname();
        }
        RoomResDto roomResponseDto = new RoomResDto(room, userDetails.getMember().getNickname(),otherNickname);
        return roomResponseDto;

    }

    public RoomResDto createRoom(RoomReqDto roomReqDto, UserDetailsImpl userDetails) {  //방 생성
        Post post = postRepository.findById(roomReqDto.getPostId()).orElseThrow(
                () -> new CustomException(ErrorCode.CANNOT_DELETE_NOT_EXIST_POST)
        );
        if(post.getMember().equals(userDetails.getMember())){   //자기 자신의 게시글에서는 방생성 불가
            throw new CustomException(ErrorCode.CANNOT_MAKE_ROOM_ALONE);
        }
        Room room = roomRepository.findRoomByJoinUserIdAndPostMemberIdAndPostPostId(userDetails.getMember().getId(), post.getMember().getId(), roomReqDto.getPostId())  //이미 만든방이 있다면 방 객체 가져옴
                .orElseGet(() ->new Room(userDetails, post));   //만들어진 방이 없다면 방 객체 새로 생성

        String otherNickname;
        if(userDetails.getMember().getId().equals(room.getJoinUserId())){   //상대방 닉네임 가져옴
            otherNickname = room.getPost().getMember().getNickname();
        }else{
            otherNickname = room.getJoinUserNickname();
        }

        roomRepository.save(room);
        RoomResDto roomResDto = new RoomResDto(room, userDetails.getMember().getNickname(),otherNickname);
        return roomResDto;
    }

    public List<RoomResDto> roomList(UserDetailsImpl userDetails) { //채팅방 리스트 가져옴
        List<Room> roomList = roomRepository.findAllByJoinUserIdOrPostMemberIdOrderByIdDesc(userDetails.getMember().getId(), userDetails.getMember().getId());

        List<RoomResDto> roomResDtos = new ArrayList<>();

        for (Room room : roomList) {    //전체 체팅방 리스트 생성
            String otherNickname;
            if(userDetails.getMember().getId().equals(room.getJoinUserId())){
                otherNickname = room.getPost().getMember().getNickname();
            }else{
                otherNickname = room.getJoinUserNickname();
            }
            roomResDtos.add(new RoomResDto(room, userDetails.getMember().getNickname(),otherNickname));
        }

        return roomResDtos;
    }

    public Chat createChat(Long roomId, ChatReqDto chatReqDto, String sendDate){ //채팅 저장
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.CANNOT_FIND_POST_NOT_EXIST) //예외처리해야함
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
