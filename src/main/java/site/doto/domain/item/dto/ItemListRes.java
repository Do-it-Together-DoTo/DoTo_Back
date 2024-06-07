package site.doto.domain.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.character.dto.CharacterDto;
import site.doto.domain.item.entity.Item;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ItemListRes {
    List<ItemDto> items = new ArrayList<>();

    public ItemListRes(List<Item> items) {
        items.stream()
                .map(ItemDto::toDto)
                .forEach(this.items::add);
    }
}
