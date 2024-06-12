package site.doto.domain.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.chatroom.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long chatRoomId);
}
