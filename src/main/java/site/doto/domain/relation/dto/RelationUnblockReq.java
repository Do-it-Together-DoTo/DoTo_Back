package site.doto.domain.relation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RelationUnblockReq {
    @NotNull
    private Long friendId;
}
