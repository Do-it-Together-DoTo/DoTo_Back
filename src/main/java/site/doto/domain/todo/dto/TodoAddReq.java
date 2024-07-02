package site.doto.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import site.doto.domain.category.entity.Category;
import site.doto.domain.member.entity.Member;
import site.doto.domain.todo.entity.Todo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TodoAddReq {
    @NotNull
    private Long categoryId;

    @NotBlank
    private String contents;

    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate date;

    public Todo toEntity(Member member, Category category) {
        return Todo.builder()
                .member(member)
                .category(category)
                .contents(contents)
                .date(date)
                .isDone(false)
                .build();
    }
}
