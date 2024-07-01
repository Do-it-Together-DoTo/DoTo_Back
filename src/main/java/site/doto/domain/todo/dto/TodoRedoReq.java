package site.doto.domain.todo.dto;

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
    private String date;

    public Todo toEntity(Member member, Category category, Todo todo, LocalDate date) {
        return Todo.builder()
                .member(member)
                .category(category)
                .contents(todo.getContents())
                .date(date)
                .isDone(false)
                .build();
    }
}
