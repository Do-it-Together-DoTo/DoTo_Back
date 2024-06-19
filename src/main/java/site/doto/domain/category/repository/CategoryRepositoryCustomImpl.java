package site.doto.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;
import site.doto.domain.category.entity.Category;
import site.doto.domain.todo.entity.Todo;

import static site.doto.domain.todo.entity.QTodo.todo;
import static site.doto.domain.betting.entity.QBetting.betting;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Todo> findTodoIfExistBetting(Category category) {
        return jpaQueryFactory.select(todo)
                .from(todo)
                .leftJoin(betting).on(todo.id.eq(betting.todo.id))
                .where(todo.category.id.eq(category.getId()))
                .fetch();
    }

}
