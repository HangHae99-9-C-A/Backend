package com.example.soldapple.websocket;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.error.ErrorCode;
import com.example.soldapple.global.dto.GlobalResDto;
import com.example.soldapple.post.entity.Post;
import com.example.soldapple.post.repository.PostRepository;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final PostRepository postRepository;

    public RoomResDto joinRoom(ChatSelectReqDto chatSelectReqDto, UserDetailsImpl userDetails) {
        Room room = roomRepository.findRoomByIdAndPostMemberId(chatSelectReqDto.getRoomId(), userDetails.getMember().getId())
                .orElseGet( () ->roomRepository.findRoomByIdAndJoinUserId(chatSelectReqDto.getRoomId(), userDetails.getMember().getId()).orElseThrow(
                                () -> new CustomException(ErrorCode.CANNOT_FIND_POST_NOT_EXIST) // 고쳐야함
                        ));
        RoomResDto roomResponseDto = new RoomResDto(room, userDetails.getMember().getNickname());
        return roomResponseDto;

    }

    public RoomResDto createRoom(RoomReqDto roomReqDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(roomReqDto.getPostId()).orElseThrow(
                () -> new CustomException(ErrorCode.CANNOT_DELETE_NOT_EXIST_POST)
        );
        if(post.getMember().equals(userDetails.getMember())){
            throw new CustomException(ErrorCode.CANNOT_MAKE_ROOM_ALONE);
        }
        //이미 만든방이 있다면 room에 저장후 리턴
        Room room = roomRepository.findRoomByJoinUserIdAndPostMemberIdAndPostPostId(userDetails.getMember().getId(), post.getMember().getId(), roomReqDto.getPostId())
                //만들어진 방이 없다면 새로 만들어서 리턴
                .orElseGet(() ->new Room(userDetails, post));

        roomRepository.save(room);
        RoomResDto roomResDto = new RoomResDto(room, userDetails.getMember().getNickname());
        return roomResDto;
    }

    public List<RoomResDto> roomList(UserDetailsImpl userDetails) {
        List<Room> roomList = roomRepository.findAllByJoinUserIdOrPostMemberIdOrderByIdDesc(userDetails.getMember().getId(), userDetails.getMember().getId());

        List<RoomResDto> roomResDtos = new ArrayList<>();

        for (Room room : roomList) {
            roomResDtos.add(new RoomResDto(room, userDetails.getMember().getNickname()));
        }

        if (roomList.isEmpty()) {
            return roomResDtos;
        } else {
            return roomResDtos;
        }
    }
}
