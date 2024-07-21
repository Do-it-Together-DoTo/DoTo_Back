package site.doto.domain.member_chat_room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.member_chat_room.entity.MemberChatRoom;
import site.doto.domain.member_chat_room.entity.MemberChatRoomPK;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, MemberChatRoomPK> {

}
