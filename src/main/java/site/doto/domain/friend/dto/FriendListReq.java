package site.doto.domain.friend.dto;

import lombok.Data;

@Data
public class FriendListReq {
    private Long lastFriendId;

    private String lastFriendTodoDate;
}
