package site.doto.domain.character.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.character.entity.Character;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CharacterListRes {
    List<CharacterDto> characters = new ArrayList<>();

    public CharacterListRes(List<Character> characters) {
        characters.stream()
                .map(CharacterDto::toDto)
                .forEach(this.characters::add);
    }
}
