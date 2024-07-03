package site.doto.domain.todo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TodoListReq {
    @NotNull
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate date;
}
