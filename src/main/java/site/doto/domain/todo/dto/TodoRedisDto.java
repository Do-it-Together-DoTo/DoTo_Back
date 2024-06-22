package site.doto.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.todo.entity.Todo;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoRedisDto implements Serializable {
    private String contents;

    private Integer year;

    private Integer month;

    private Integer date;

    public static TodoRedisDto toDto(Todo todo) {
        return TodoRedisDto.builder()
                .contents(todo.getContents())
                .year(todo.getDate().getYear())
                .month(todo.getDate().getMonthValue())
                .date(todo.getDate().getDayOfMonth())
                .build();
    }
}