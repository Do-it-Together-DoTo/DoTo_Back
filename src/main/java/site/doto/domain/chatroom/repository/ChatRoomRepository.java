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
            "where c.betting in " +
            "(select b " +
            "from Betting b " +
            "where b.isAchieved is not null)")
    void detachBettingFromChatRoom();

    @Modifying
    @Query("delete " +
            "from ChatRoom c " +
            "where c.betting is null")
    void deleteOrphanChatRoom();
}
