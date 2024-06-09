package site.doto.domain.todo.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.todo.entity.Todo;

@Data
@Builder
public class TodoRecordDto {
    private String name;

    private Integer total;

    private Integer achieved;

    public static TodoRecordDto toDto(Todo todo, Integer total, Integer achieved) {
        return TodoRecordDto.builder()
                .name(todo.getCategory().getContents())
                .total(total)
                .achieved(achieved)
                .build();
    }
}
