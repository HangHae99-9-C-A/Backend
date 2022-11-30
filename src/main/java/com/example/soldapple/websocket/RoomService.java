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
        if(chatSelectReqDto.getRoomId() == 1){

            Room room = roomRepository.findRoomByPostPostIdAndJoinUserNickname(chatSelectReqDto.getPostId(), userDetails.getMember().getNickname()).orElseThrow(
                    ()-> new CustomException(ErrorCode.CANNOT_FIND_POST_NOT_EXIST)
            );
            RoomResDto roomResponseDto = new RoomResDto(room);
            return roomResponseDto;
        }else{
            Room room = roomRepository.findById(chatSelectReqDto.getRoomId()).orElseThrow(
                    ()-> new CustomException(ErrorCode.CANNOT_FIND_POST_NOT_EXIST)
            );
            RoomResDto roomResponseDto = new RoomResDto(room);
            return roomResponseDto;
        }
    }

    public RoomResDto createRoom(RoomReqDto roomReqDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(roomReqDto.getPostId()).orElseThrow(
                ()-> new CustomException(ErrorCode.CANNOT_FIND_POST_NOT_EXIST)
        );
//        if(post.getMember().getId().equals(userDetails.getMember().getId())){
//            throw new CustomException(ErrorCode.SameUser);
//        }
        //이미 만든방이 있다면 room에 저장후 리턴
        Room room = roomRepository.findRoomByJoinUserIdAndPostUserIdAndPostPostId(userDetails.getMember().getId(), post.getMember().getId(), roomReqDto.getPostId())
                //만들어진 방이 없다면 새로 만들어서 리턴
                .orElse(new Room(post.getMember().getId(), roomReqDto,userDetails, post));

        roomRepository.save(room);
        RoomResDto roomResDto = new RoomResDto(room);
        return roomResDto;
    }

    public List<RoomResDto> roomList(UserDetailsImpl userDetails) {
        List<Room> roomList = roomRepository.findAllByJoinUserIdOrPostUserIdOrderByIdDesc(userDetails.getMember().getId(),userDetails.getMember().getId());

        List<RoomResDto> roomResDtos = new ArrayList<>();

        for(Room room : roomList ){
            roomResDtos.add(new RoomResDto(room));
        }

        if(roomList.isEmpty()){
            return roomResDtos;
        }else{
            return roomResDtos;
        }
    }
}
