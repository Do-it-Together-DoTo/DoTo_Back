package site.doto.domain.category.dto;

import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.member.entity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryAddReq {
    @NotBlank
    private String contents;

    @NotNull
    private Boolean isPublic;

    @NotNull
    private String color;

    public Category toEntity(Member member, int seq) {
        return Category.builder()
                .member(member)
                .contents(contents)
                .isPublic(isPublic)
                .isActivated(true)
                .seq(seq+1)
                .color(Color.valueOf(color))
                .build();
    }
}
