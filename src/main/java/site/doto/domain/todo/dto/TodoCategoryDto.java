package site.doto.domain.todo.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.category.enums.Color;

import java.util.List;

@Data
@Builder
public class TodoCategoryDto {
    private Long categoryId;

    private String categoryContents;

    private Boolean categoryIsActivated;

    private Color categoryColor;

    private List<TodoDetailsRes> todoDetailsResList;
}
