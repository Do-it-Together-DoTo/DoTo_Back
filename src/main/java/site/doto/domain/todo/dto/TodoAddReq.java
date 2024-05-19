package site.doto.domain.todo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TodoAddReq {
    private Long categoryId;

    private String date;

    @NotNull
    private String contents;
}
