package site.doto.domain.item_type.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.item_type.entity.ItemType;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class StoreItemListRes {
    List<ItemTypeDto> items = new ArrayList<>();

    public StoreItemListRes(List<ItemType> items) {
        items.stream()
                .map(ItemTypeDto::toDto)
                .forEach(this.items::add);
    }
}
