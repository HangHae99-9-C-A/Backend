package com.example.soldapple.websocket.repository;

import com.example.soldapple.websocket.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByRoom_PostPostId(Long roomId);
}
