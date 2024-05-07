package site.doto.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import site.doto.global.common.code.ErrorCode;
import site.doto.global.common.code.SuccessCode;

@Data
@AllArgsConstructor
public class ResponseDto<T> {
    private ApiResponseHeader header;

    private T body;

    public static <T> ResponseDto<T> success(SuccessCode s, T body) {
        return new ResponseDto<>(new ApiResponseHeader(s), body);
    }

    public static <T> ResponseDto<T> fail(ErrorCode e) {
        return new ResponseDto<>(new ApiResponseHeader(e), null);
    }

    public static <T> ResponseDto<T> fail(String errorMessage) {
        return new ResponseDto<>(new ApiResponseHeader(HttpStatus.BAD_REQUEST.value(), errorMessage), null);
    }

}
