package site.doto.domain.friend.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendListReq {
    @NotNull
    private Long lastFriendId;

    @NotNull
    private String lastFriendTodoDate;
}
