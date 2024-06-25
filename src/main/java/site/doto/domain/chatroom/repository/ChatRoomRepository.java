package site.doto.domain.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.doto.domain.chatroom.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long chatRoomId);

    @Modifying
    @Query("update " +
            "ChatRoom c " +
            "set c.betting = null " +
            "where c.betting.id in " +
            "(select b.id " +
            "from Betting b " +
            "where b.todo is null " +
            "or b.todo.id in " +
            "(select t.id " +
            "from Todo t " +
            "where t.date < current_date))")
    void detachBettingFromChatRoom();

    @Modifying
    @Query("delete " +
            "from ChatRoom c " +
            "where c.betting is null")
    void deleteOrphanChatRoom();
}
