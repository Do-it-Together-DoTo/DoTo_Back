package site.doto.domain.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.category.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategoryListRes {
    private List<CategoryDetailsRes> activated;

    private List<CategoryDetailsRes> inactivated;

    public CategoryListRes(List<Category> activated, List<Category> inactivated) {
        this.activated = activated.stream()
                .map(CategoryDetailsRes::toDto)
                .collect(Collectors.toList());

        this.inactivated = inactivated.stream()
                .map(CategoryDetailsRes::toDto)
                .collect(Collectors.toList());
    }
}
