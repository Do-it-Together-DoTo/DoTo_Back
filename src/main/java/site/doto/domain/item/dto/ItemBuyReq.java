package site.doto.domain.item.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemBuyReq {
    @NotNull
    private Integer count;
}
