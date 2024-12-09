package site.doto.domain.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoChangeDoneReq {
    @NotNull
    private Boolean isDone;
}
