package site.doto.domain.category.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Scope {
    PUBLIC,
    FRIENDS,
    PRIVATE;
}