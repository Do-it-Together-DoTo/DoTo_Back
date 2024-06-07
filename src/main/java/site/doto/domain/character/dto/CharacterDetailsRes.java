package site.doto.domain.character.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CharacterDetailsRes {
    private Long id;

    private String name;

    private String img;

    private Integer price;

}
