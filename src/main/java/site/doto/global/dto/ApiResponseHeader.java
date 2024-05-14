package site.doto.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.doto.global.status_code.ErrorCode;
import site.doto.global.status_code.SuccessCode;

@Data
@AllArgsConstructor
public class ApiResponseHeader {
    private int httpStatusCode;
    private String message;

    public ApiResponseHeader(SuccessCode s) {
        this.httpStatusCode = s.getHttpStatusCode();
        this.message = s.getMessage();
    }

    public ApiResponseHeader(ErrorCode e) {
        this.httpStatusCode = e.getHttpStatusCode();
        this.message = e.getMessage();
    }

}
