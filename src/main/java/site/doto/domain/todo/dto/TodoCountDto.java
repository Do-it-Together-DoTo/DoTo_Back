package site.doto.domain.todo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoCountDto {
    private Integer day;

    private Integer finishedTodo;

    private Integer ongoingTodo;
}
