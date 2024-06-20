package site.doto.domain.relation.dto;

import lombok.Data;

@Data
public class RelationListReq {
    private Long lastFriendId;

    private String lastFriendTodoDate;
}
