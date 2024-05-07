package site.doto.global.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.doto.global.common.dto.ResponseDto;
import site.doto.global.exception.CustomException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseDto<?> handleCustomException(CustomException e){
        return ResponseDto.fail(e.getErrorCode());
    }

}