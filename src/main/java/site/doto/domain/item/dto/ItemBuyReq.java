package site.doto.domain.item.dto;

import lombok.Data;
import site.doto.domain.item.entity.Item;
import site.doto.domain.item.entity.ItemPK;
import site.doto.domain.item.entity.ItemType;
import site.doto.domain.member.entity.Member;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
