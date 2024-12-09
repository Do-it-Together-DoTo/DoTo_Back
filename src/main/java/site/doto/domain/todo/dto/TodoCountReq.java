package site.doto.domain.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoCountReq {
    @NotNull
    private Integer year;

    @NotNull
    private Integer month;
}
