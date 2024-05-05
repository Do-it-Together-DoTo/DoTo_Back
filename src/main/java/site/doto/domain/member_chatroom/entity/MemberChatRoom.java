package site.doto.domain.member_chatroom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.chatroom.entity.ChatRoom;
import site.doto.domain.member.entity.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberChatRoom implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;

}
