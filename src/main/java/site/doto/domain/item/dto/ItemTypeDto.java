package site.doto.domain.item.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.item.entity.ItemType;

@Data
@Builder
public class ItemTypeDto {
    private Long id;

    private String name;

    private String img;

    private Integer price;

    private String grade;


    public static ItemTypeDto toDto(ItemType itemType) {
        return ItemTypeDto.builder()
                .id(itemType.getId())
                .name(itemType.getName())
                .img(itemType.getImg())
                .price(itemType.getPrice())
                .grade(itemType.getGrade())
                .build();
    }
}
