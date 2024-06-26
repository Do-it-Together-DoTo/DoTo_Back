package site.doto.domain.category.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.category.enums.Scope;
import site.doto.domain.member.entity.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryAddReq {
    @NotBlank
    @Length(max = 20)
    private String contents;

    @NotNull
    private String scope;

    @NotNull
    private String color;

    public Category toEntity(Member member, Integer seq) {
        return Category.builder()
                .member(member)
                .contents(contents)
                .scope(Scope.valueOf(scope.toUpperCase()))
                .isActivated(true)
                .seq(seq+1)
                .color(Color.valueOf(color.toUpperCase()))
                .build();
    }
}
