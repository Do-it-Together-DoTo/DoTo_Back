package site.doto.domain.todo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TodoListRes {
    private List<TodoCategoryDto> todoList = new ArrayList<>();
}
