package site.doto.domain.character.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.character.entity.Character;

@Data
@Builder
public class CharacterDto {
    private Long id;

    private String name;

    private String img;

    private Integer level;

    private Integer exp;

    private String description;

    public static CharacterDto toDto(Character character) {
        return CharacterDto.builder()
                .id(character.getId())
                .name(character.getCharacterType().getName())
                .img(character.getCharacterType().getImg())
                .level(character.getCharacterType().getLevel())
                .exp(character.getExp())
                .description(character.getCharacterType().getDescription())
                .build();
    }
}
