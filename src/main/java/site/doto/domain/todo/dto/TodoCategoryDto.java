package site.doto.domain.todo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TodoCategoryDto {
    private Long categoryId;

    private List<TodoDetailsRes> todoDetailsResList;
}
