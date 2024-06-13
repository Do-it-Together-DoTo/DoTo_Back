package site.doto.domain.todo.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.todo.entity.Todo;

@Data
@Builder
public class TodoDetailsRes {
    private Long id;

    private String contents;

    private String date;

    private Boolean isDone;

    public static TodoDetailsRes toDto(Todo todo) {
        return TodoDetailsRes.builder()
                .id(todo.getId())
                .contents(todo.getContents())
                .date(todo.getDate().toString())
                .isDone(todo.getIsDone())
                .build();
    }
}
