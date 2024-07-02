package site.doto.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.member.entity.Member;
import site.doto.domain.todo.entity.Todo;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TodoRedoReq {
    @NotNull
    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate date;

    public Todo toEntity(Member member, Category category, Todo todo) {
        return Todo.builder()
                .member(member)
                .category(category)
                .contents(todo.getContents())
                .date(date)
                .isDone(false)
                .build();
    }
}
