package site.doto.domain.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TodoRedoReq {
    @NotNull
    private Long id;

    @NotNull
    private String date;
}
