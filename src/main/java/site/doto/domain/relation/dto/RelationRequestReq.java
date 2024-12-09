package site.doto.domain.relation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RelationRequestReq {
    @NotNull
    private Long friendId;
}
