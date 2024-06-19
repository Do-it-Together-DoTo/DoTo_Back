package site.doto.domain.relation.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RelationRequestReq {
    @NotNull
    private Long friendId;
}
