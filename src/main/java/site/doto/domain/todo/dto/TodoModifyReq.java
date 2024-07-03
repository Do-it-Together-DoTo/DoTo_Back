package site.doto.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TodoModifyReq {
    private String contents;

    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate date;
}
