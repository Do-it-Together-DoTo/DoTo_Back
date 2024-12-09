package site.doto.domain.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemUseReq {
    @NotNull
    private Long characterId;

    @NotNull
    private Long itemTypeId;

    @NotNull
    private Integer count;

}
