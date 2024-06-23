package site.doto.domain.character.dto;

import lombok.Data;
import site.doto.domain.character.entity.Character;
import site.doto.domain.character.entity.CharacterType;
import site.doto.domain.member.entity.Member;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CharacterBuyReq {
    @NotNull
    @Min(1)
    private Integer count;

    public Character toEntity(Member member, CharacterType characterType) {
        return Character.builder()
                .member(member)
                .characterType(characterType)
                .exp(0)
                .build();
    }
}
