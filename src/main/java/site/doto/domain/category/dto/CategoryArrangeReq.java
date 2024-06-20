package site.doto.domain.category.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryArrangeReq {
    private Boolean isActivated;

    private List<Long> categoryIds;

    private List<Integer> orders;
}
