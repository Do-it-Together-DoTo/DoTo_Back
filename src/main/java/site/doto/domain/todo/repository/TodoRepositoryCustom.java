package site.doto.domain.todo.repository;

import site.doto.domain.category.entity.Category;
import site.doto.domain.todo.entity.Todo;

public interface TodoRepositoryCustom {
    Todo findTodoIfOngoingBetting(Category category);
}