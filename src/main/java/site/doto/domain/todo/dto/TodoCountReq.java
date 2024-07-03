package site.doto.domain.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TodoCountReq {
    @NotNull
    private String date;
}
