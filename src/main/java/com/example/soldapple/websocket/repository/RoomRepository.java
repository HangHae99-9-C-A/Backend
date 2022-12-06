package com.example.soldapple.websocket.repository;

import com.example.soldapple.websocket.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByJoinUserIdOrPostMemberIdOrderByIdDesc(Long joinUserId, Long postUserId);
    Optional<Room> findRoomByIdAndJoinUserId(Long roomId,  Long joinUserId);
    Optional<Room> findRoomByIdAndPostMemberId(Long roomId, Long postUserId);
    Optional<Room> findRoomByJoinUserIdAndPostMemberIdAndPostPostId(Long joinUserId, Long postUserId, Long postId);
}
