package com.example.soldapple.websocket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByJoinUserIdOrPostUserIdOrderByIdDesc(Long joinUserId, Long postUserId);

    Optional<Room> findRoomByPostPostIdAndJoinUserNickname(Long postId, String joinUser);

    Optional<Room> findRoomByJoinUserIdAndPostUserIdAndPostPostId(Long joinUserId, Long postUserId, Long postId);
}
