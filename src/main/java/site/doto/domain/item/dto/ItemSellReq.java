package site.doto.domain.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemSellReq {
    @NotNull
    private Integer count;

}
