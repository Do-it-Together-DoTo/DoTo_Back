package site.doto.domain.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.item.entity.ItemType;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class StoreItemListRes {
    private List<ItemTypeDto> items = new ArrayList<>();

    public StoreItemListRes(List<ItemType> items) {
        items.stream()
                .map(ItemTypeDto::toDto)
                .forEach(this.items::add);
    }
}
