package site.doto.domain.category.dto;

import lombok.Data;

@Data
public class CategoryModifyReq {
    private String contents;

    private String scope;

    private Boolean isActivated;

    private String color;
}
