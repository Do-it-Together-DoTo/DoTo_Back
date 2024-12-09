package site.doto.domain.item.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import site.doto.domain.item.entity.Item;
import site.doto.domain.item.entity.ItemPK;
import site.doto.domain.item.entity.ItemType;
import site.doto.domain.member.entity.Member;

@Data
public class ItemBuyReq {
    @NotNull
    @Min(1)
    private Integer count;

    public Item toEntity(Member member, ItemType itemType, ItemPK itemPK) {
        return Item.builder()
                .itemPK(itemPK)
                .member(member)
                .itemType(itemType)
                .count(count)
                .build();
    }
}
