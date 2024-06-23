package site.doto.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import site.doto.domain.category.entity.Category;
import site.doto.domain.todo.entity.Todo;

import static site.doto.domain.todo.entity.QTodo.todo;
import static site.doto.domain.betting.entity.QBetting.betting;

@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Todo> findTodoIfExistBetting(Category category) {
        return jpaQueryFactory.select(todo)
                .from(todo)
                .innerJoin(betting).on(todo.id.eq(betting.todo.id))
                .where(todo.category.id.eq(category.getId()))
                .fetch();
    }

    @Override
    public List<Todo> findTodoIfOngoingBetting(Category category) {
        return jpaQueryFactory.select(todo)
                .from(todo)
                .innerJoin(betting).on(todo.id.eq(betting.todo.id))
                .where(todo.category.id.eq(category.getId()))
                .where(todo.date.goe(LocalDate.now()))
                .fetch();
    }
}
