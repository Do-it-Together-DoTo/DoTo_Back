package site.doto.domain.friend.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendDeclinedReq {
    @NotNull
    private Long toMemberId;
}
