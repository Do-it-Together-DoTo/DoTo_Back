package site.doto.domain.category.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;

@Data
@Builder
public class CategoryDetailsRes {
    private Long id;

    private String contents;

    private Boolean isPublic;

    private Boolean isActivated;

    private Color color;

    private Integer seq;

    public static CategoryDetailsRes toDto(Category category) {
        return CategoryDetailsRes.builder()
                .id(category.getId())
                .contents(category.getContents())
                .isPublic(category.getIsPublic())
                .isActivated(category.getIsActivated())
                .color(category.getColor())
                .seq(category.getSeq())
                .build();
    }
}
