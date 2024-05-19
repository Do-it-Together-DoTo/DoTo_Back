package site.doto.domain.todo.dto;

import lombok.Data;

@Data
public class TodoRedoReq {
    private Long id;

    private String date;
}
