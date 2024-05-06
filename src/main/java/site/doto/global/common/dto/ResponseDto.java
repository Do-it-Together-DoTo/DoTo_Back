package site.doto.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {
    private final static int SUCCESS = 200;

    private final static int FAILED = 500;

    private final static String SUCCESS_MESSAGE = "성공적으로 처리되었습니다.";

    private final static String FAILED_MESSAGE = "서버 내부 오류가 발생했습니다.";

    private ApiResponseHeader header;

    private T body;

    public static <T> ResponseDto<T> success(T body) {
        return new ResponseDto<>(new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), body);
    }

    public static <T> ResponseDto<T> fail(T body) {
        return new ResponseDto<>(new ApiResponseHeader(FAILED, FAILED_MESSAGE), null);
    }
}
