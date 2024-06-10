package site.doto.domain.item.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemUseReq {
    @NotNull
    private Long characterId;

    @NotNull
    private Long itemTypeId;

    @NotNull
    private Integer count;

}
