package site.doto.domain.chatroom.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.doto.domain.chatroom.dto.ChatRoomDto;
import site.doto.domain.chatroom.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long chatRoomId);

    @Query("select " +
            "new site.doto.domain.chatroom.dto.ChatRoomDto(cr.id, b.id, b.name, m.id, m.nickname, t.img) " +
            "from ChatRoom cr " +
            "join cr.betting b " +
            "join b.member m " +
            "join m.mainCharacter c " +
            "join c.characterType t " +
            "left join MemberChatRoom mc on mc.chatRoom = cr " +
            "where mc.member.id = :memberId")
    List<ChatRoomDto> findChatRoomById(@Param("memberId") Long memberId);

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
