package site.doto.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreItemDetailsRes {
    private String img;

    private String name;

    private String description;
    
}
