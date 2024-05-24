package site.doto.domain.friend.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendBlockReq {
    @NotNull
    private Long memberId;
}
