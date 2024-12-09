package site.doto.domain.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TodoListReq {
    @NotNull
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate date;
}
