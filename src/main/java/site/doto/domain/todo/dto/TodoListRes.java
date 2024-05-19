package site.doto.domain.todo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.todo.entity.Todo;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TodoListRes {
    List<TodoDetailsRes> todoList = new ArrayList<>();

    public TodoListRes(List<Todo> todoList) {
        todoList.stream()
                .map(TodoDetailsRes::toDto)
                .forEach(this.todoList::add);
    }
}
