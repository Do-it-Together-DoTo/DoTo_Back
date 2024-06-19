package site.doto.domain.relation.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RelationCanceledReq {
    @NotNull
    private Long toMemberId;
}
