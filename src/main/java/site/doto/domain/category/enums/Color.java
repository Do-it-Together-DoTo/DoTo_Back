package site.doto.domain.category.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
    SKYBLUE("skyblue"),
    PINK("pink"),
    BLUE("blue"),
    SALMON("salmon"),
    PURPLE("purple"),
    YELLOW("yellow"),
    GREEN("green"),
    ;

    String color;
}
