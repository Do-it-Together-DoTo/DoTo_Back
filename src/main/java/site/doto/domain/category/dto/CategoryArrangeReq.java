package site.doto.domain.category.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryArrangeReq {
    private List<Long> activated;

    private List<Long> inactivated;
}
