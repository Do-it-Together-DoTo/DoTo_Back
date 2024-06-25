package site.doto.domain.category.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.category.enums.Scope;

@Data
@Builder
public class CategoryDetailsRes {
    private Long id;

    private String contents;

    private Scope scope;

    private Boolean isActivated;

    private Color color;

    private Integer seq;

    public static CategoryDetailsRes toDto(Category category) {
        return CategoryDetailsRes.builder()
                .id(category.getId())
                .contents(category.getContents())
                .scope(category.getScope())
                .isActivated(category.getIsActivated())
                .color(category.getColor())
                .seq(category.getSeq())
                .build();
    }
}
