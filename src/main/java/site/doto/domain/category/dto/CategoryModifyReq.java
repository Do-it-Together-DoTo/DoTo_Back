package site.doto.domain.category.dto;

import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.member.entity.Member;

@Data
public class CategoryModifyReq {
    private String contents;

    private Boolean isPublic;

    private Boolean isActivated;

    private String color;

    public Category toEntity(Member member) {
        return Category.builder()
                .member(member)
                .contents(contents)
                .isPublic(isPublic)
                .isActivated(isActivated)
                .color(Color.valueOf(color))
                .build();
    }
}
