package site.doto.domain.category.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryArrangeReq {
    List<Long> categoryIds;
    List<Integer> orders;
}
