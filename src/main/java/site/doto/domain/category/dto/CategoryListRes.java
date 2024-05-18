package site.doto.domain.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.category.entity.Category;

import java.util.List;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class CategoryListRes {
    List<CategoryDetailsRes> activated = new ArrayList<>();

    List<CategoryDetailsRes> inactivated = new ArrayList<>();

    public CategoryListRes(List<Category> activated, List<Category> inactivated) {
        activated.stream()
                .map(CategoryDetailsRes::toDto)
                .forEach(this.activated::add);

        inactivated.stream()
                .map(CategoryDetailsRes::toDto)
                .forEach(this.inactivated::add);
    }
}
