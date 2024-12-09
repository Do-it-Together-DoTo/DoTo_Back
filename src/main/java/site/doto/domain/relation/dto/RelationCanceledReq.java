package site.doto.domain.relation.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class RelationCanceledReq {
    @NotNull
    private Long friendId;
}
