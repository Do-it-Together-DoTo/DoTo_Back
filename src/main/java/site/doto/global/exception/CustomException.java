package site.doto.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.doto.global.common.code.ErrorCode;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    ErrorCode errorCode;

}