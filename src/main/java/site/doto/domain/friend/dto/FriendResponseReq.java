package site.doto.domain.friend.dto;

import lombok.Data;
import site.doto.domain.friend.entity.Friend;
import site.doto.domain.friend.enums.FriendRelation;
import site.doto.domain.member.entity.Member;

import javax.validation.constraints.NotNull;

@Data
public class FriendResponseReq {
    @NotNull
    private Long toMemberId;

    public Friend toEntity(Member fromMember, Member toMember) {
        return Friend.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .status(FriendRelation.ACCEPTED)
                .build();
    }
}
