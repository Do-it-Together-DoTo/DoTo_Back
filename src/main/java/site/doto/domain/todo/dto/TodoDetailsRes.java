package site.doto.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.doto.domain.todo.entity.Todo;

@Data
@Builder
@AllArgsConstructor
public class TodoDetailsRes {
    private Long id;

    private String contents;

    private Boolean isDone;

    public static TodoDetailsRes toDto(Todo todo) {
        return TodoDetailsRes.builder()
                .id(todo.getId())
                .contents(todo.getContents())
                .isDone(todo.getIsDone())
                .build();
    }
}
