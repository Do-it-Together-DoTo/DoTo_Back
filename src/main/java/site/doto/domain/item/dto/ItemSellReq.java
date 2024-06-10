package site.doto.domain.item.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemSellReq {
    @NotNull
    private Integer count;

}
