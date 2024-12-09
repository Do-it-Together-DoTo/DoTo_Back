package site.doto.domain.relation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RelationBlockReq {
    @NotNull
    private Long friendId;
}
