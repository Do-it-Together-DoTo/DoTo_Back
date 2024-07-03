package site.doto.domain.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TodoCountReq {
    @NotNull
    private Integer year;

    @NotNull
    private Integer month;
}
