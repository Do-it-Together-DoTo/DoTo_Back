package site.doto.domain.todo.repository;

import site.doto.domain.category.entity.Category;
import site.doto.domain.todo.dto.TodoDetailsRes;
import site.doto.domain.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepositoryCustom {
    Todo findTodoIfOngoingBetting(Category category);

    List<TodoDetailsRes> findTodoDetailsByCategory(Long categoryId, LocalDate date);
}