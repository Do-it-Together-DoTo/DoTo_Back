package site.doto.domain.item.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.item.entity.Item;

@Data
@Builder
public class ItemDto {
    private Long id;

    private String name;

    private String img;

    private Integer count;

    private String grade;

    public static ItemDto toDto(Item item) {
        return ItemDto.builder()
                .id(item.getItemType().getId())
                .name(item.getItemType().getName())
                .img(item.getItemType().getImg())
                .count(item.getCount())
                .grade(item.getItemType().getGrade())
                .build();
    }
}
