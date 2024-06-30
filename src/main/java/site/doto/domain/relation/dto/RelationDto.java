package site.doto.domain.relation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.doto.domain.member.entity.Member;

@Data
@Builder
@AllArgsConstructor
public class RelationDto {
    private Long memberId;

    private String nickname;

    private String mainCharacterImg;

    public RelationDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.mainCharacterImg = member.getMainCharacterImg();
    }
}
