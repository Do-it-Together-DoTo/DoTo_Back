package site.doto.domain.todo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TodoCountRes {
    private List<TodoCountDto> countList = new ArrayList<>();
}
