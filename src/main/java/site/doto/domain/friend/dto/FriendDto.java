package site.doto.domain.friend.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.friend.entity.Friend;
import site.doto.domain.friend.enums.FriendRelation;

@Data
@Builder
public class FriendDto {
    private Long fromMemberId;

    private Long toMemberId;

    private FriendRelation status;

    public static FriendDto toDto(Friend friend) {
        return FriendDto.builder()
                .fromMemberId(friend.getFromMember().getId())
                .toMemberId(friend.getToMember().getId())
                .status(friend.getStatus())
                .build();
    }
}
