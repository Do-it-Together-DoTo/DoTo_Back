package site.doto.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.doto.domain.item.entity.ItemType;

@Data
@AllArgsConstructor
@Builder
public class StoreItemDetailsRes {
    private String img;

    private String name;

    private String description;

    public static StoreItemDetailsRes toDto(ItemType itemType) {
        return StoreItemDetailsRes.builder()
                .img(itemType.getImg())
                .name(itemType.getName())
                .description(itemType.getDescription()).build();
    }
}
