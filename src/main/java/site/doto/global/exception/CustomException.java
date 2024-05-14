package site.doto.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.doto.global.status_code.ErrorCode;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    ErrorCode errorCode;

}