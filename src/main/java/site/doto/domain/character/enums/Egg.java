package site.doto.domain.character.enums;

import lombok.Getter;

@Getter
public enum Egg {
    EGG("알", "이미지", 100)
    ;

    private final String name;

    private final String img;

    private final Integer cost;

    private Egg(String name, String img, Integer cost) {
        this.name = name;
        this.img = img;
        this.cost = cost;
    }
}
