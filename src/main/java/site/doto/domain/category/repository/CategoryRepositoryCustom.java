package site.doto.domain.category.repository;

import site.doto.domain.category.entity.Category;
import site.doto.domain.todo.entity.Todo;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Todo> findTodoIfExistBetting(Category category);
}