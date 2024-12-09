package site.doto.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.doto.domain.category.entity.Category;
import site.doto.domain.todo.dto.TodoDetailsRes;
import site.doto.domain.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;

import static site.doto.domain.betting.entity.QBetting.betting;
import static site.doto.domain.todo.entity.QTodo.todo;


@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Todo findTodoIfOngoingBetting(Category category) {
        return jpaQueryFactory.select(todo)
                .from(todo)
                .innerJoin(betting).on(todo.id.eq(betting.todo.id))
                .where(todo.category.id.eq(category.getId()))
                .where(todo.date.goe(LocalDate.now()))
                .fetchFirst();
    }

    @Override
    public List<TodoDetailsRes> findTodoDetailsByCategory(Long categoryId, LocalDate date) {
        return jpaQueryFactory.select(Projections.constructor(
                        TodoDetailsRes.class, todo.id, todo.contents, todo.isDone))
                .from(todo)
                .where(todo.date.eq(date))
                .where(todo.category.id.eq(categoryId))
                .fetch();
    }
}
