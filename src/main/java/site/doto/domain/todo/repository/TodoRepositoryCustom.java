package site.doto.domain.todo.repository;

import site.doto.domain.category.entity.Category;
import site.doto.domain.todo.entity.Todo;

import java.util.List;

public interface TodoRepositoryCustom {
    List<Todo> findTodoIfExistBetting(Category category);
    List<Todo> findTodoIfOngoingBetting(Category category);
}