package site.doto.domain.relation.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class RelationResponseReq {
    @NotNull
    private Long friendId;
}
