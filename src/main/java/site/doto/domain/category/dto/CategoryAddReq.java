package site.doto.domain.category.dto;

import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.member.entity.Member;

import javax.validation.constraints.NotNull;

@Data
public class CategoryAddReq {
    @NotNull
    private String contents;

    @NotNull
    private Boolean isPublic;

    @NotNull
    private String color;

    public Category toEntity(Member member) {
        return Category.builder()
                .member(member)
                .contents(contents)
                .isPublic(isPublic)
                .isActivated(true)
                .color(Color.valueOf(color))
                .build();
    }
}
