package site.doto.domain.relation.dto;

import lombok.Data;
import site.doto.domain.member.entity.Member;

@Data
public class RelationDetailDto {
    private Long memberId;

    private String nickname;

    private String description;

    private String mainCharacterImg;

    private Integer mainCharacterExp;

    private Integer mainCharacterLevel;

    public RelationDetailDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.description = member.getDescription();
        this.mainCharacterImg = member.getMainCharacterImg();
        this.mainCharacterExp = member.getMainCharacter().getExp();
        this.mainCharacterLevel = member.getMainCharacter().getExp() / 100 + 1;
    }
}
