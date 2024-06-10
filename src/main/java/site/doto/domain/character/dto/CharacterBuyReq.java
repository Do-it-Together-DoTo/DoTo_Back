package site.doto.domain.character.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CharacterBuyReq {
    @NotNull
    private Integer count;
}
