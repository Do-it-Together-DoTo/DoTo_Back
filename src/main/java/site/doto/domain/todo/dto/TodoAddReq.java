package site.doto.domain.todo.dto;

import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.member.entity.Member;
import site.doto.domain.todo.entity.Todo;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TodoAddReq {
    @NotNull
    private Long categoryId;

    @NotNull
    private String contents;

    public Todo toEntity(Member member, Category category) {
        return Todo.builder()
                .member(member)
                .category(category)
                .contents(contents)
                .date(LocalDate.now())
                .isDone(false)
                .build();
    }
}
