package site.doto.global.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.doto.global.common.dto.ResponseDto;
import site.doto.global.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseDto<?> handleException(Exception e) {
        return ResponseDto.fail(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseDto<?> handleCustomException(CustomException e) {
        return ResponseDto.fail(e.getErrorCode());
    }

}