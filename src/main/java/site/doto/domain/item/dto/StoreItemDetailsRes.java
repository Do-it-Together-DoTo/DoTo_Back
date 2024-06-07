package site.doto.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreItemDetailsRes {
    private Integer exp;

    private String description;
}
