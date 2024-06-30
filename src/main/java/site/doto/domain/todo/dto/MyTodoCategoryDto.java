package site.doto.domain.todo.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.category.enums.Color;
import site.doto.domain.category.enums.Scope;

import java.util.List;

@Data
@Builder
public class MyTodoCategoryDto {
    private Long categoryId;

    private String categoryContents;

    private Boolean categoryIsActivated;

    private Color categoryColor;

    private Scope categoryScope;

    private List<TodoDetailsRes> todoDetailsResList;
}
