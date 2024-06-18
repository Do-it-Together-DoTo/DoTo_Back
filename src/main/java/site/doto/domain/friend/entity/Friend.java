package site.doto.domain.friend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.friend.enums.FriendRelation;
import site.doto.domain.member.entity.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend implements Serializable {
    @EmbeddedId
    private FriendPK friendPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("toMemberId")
    @JoinColumn(name = "to_member_id")
    private Member toMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("fromMemberId")
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    @Enumerated(EnumType.STRING)
    private FriendRelation status;

}
