package site.doto.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseHeader {
    private int httpStatusCode;
    private String message;
}
