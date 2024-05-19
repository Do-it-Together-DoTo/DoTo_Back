package site.doto.domain.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TodoModifyReq {
    @NotNull
    private String contents;

    private String date;
}
