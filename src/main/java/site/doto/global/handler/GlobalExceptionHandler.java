package site.doto.global.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import site.doto.global.dto.ResponseDto;
import site.doto.global.exception.CustomException;
import site.doto.global.status_code.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseDto<?> handleException(Exception e) {
        return ResponseDto.fail(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseDto<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseDto.fail(ErrorCode.CRITERIA_MISMATCH);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseDto<?> handleBindException(BindException e) {
        return ResponseDto.fail(ErrorCode.BIND_EXCEPTION);
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseDto<?> handleCustomException(CustomException e) {
        return ResponseDto.fail(e.getErrorCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseDto<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResponseDto.fail(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseDto<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseDto.fail(ErrorCode.BAD_REQUEST);
    }
}