package site.doto.domain.todo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.todo.entity.Todo;

import java.util.ArrayList;
import java.util.List;

@Data
public class TodoListRes {
    List<TodoCategoryDto> todoList = new ArrayList<>();
}
