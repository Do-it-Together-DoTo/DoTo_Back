package site.doto.global.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import site.doto.global.dto.ResponseDto;
import site.doto.global.exception.CustomException;
import site.doto.global.status_code.ErrorCode;

@RestControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseDto<?> handleException(Exception e) {
        return ResponseDto.fail(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseDto<?> handleCustomException(CustomException e) {
        return ResponseDto.fail(e.getErrorCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseDto<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseDto.fail(ErrorCode.NOT_FOUND);
    }
}