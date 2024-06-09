package site.doto.domain.friend.dto;

import lombok.Data;
import site.doto.domain.friend.entity.Friend;
import site.doto.domain.friend.enums.FriendRelation;
import site.doto.domain.member.entity.Member;

import javax.validation.constraints.NotNull;

@Data
public class FriendResponseReq {
    @NotNull
    private Long fromMemberId;

    public Friend toEntity(Member toMember, Member fromMember) {
        return Friend.builder()
                .toMember(toMember)
                .fromMember(fromMember)
                .status(FriendRelation.ACCEPTED)
                .build();
    }
}
