package site.doto.domain.betting.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.member.entity.Member;
import site.doto.domain.todo.entity.Todo;

import javax.validation.constraints.NotNull;

@Data
public class BettingAddReq {
    @NotNull
    Long todoId;

    @NotNull
    @Length(min = 2, max = 20)
    String name;

    public Betting toEntity(Member member, Todo todo) {
        return Betting.builder()
                .member(member)
                .todo(todo)
                .name(name)
                .isAchieved(false)
                .build();
    }
}
