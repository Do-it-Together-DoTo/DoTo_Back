package site.doto.domain.relation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RelationDeclinedReq {
    @NotNull
    private Long friendId;
}
