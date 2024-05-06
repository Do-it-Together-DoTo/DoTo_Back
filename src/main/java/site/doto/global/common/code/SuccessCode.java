package site.doto.global.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    ;

    private final int httpStatusCode;
    private final String message;
}
