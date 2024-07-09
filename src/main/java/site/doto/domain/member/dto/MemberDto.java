package site.doto.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.doto.domain.relation.enums.RelationStatus;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long memberId;

    private String nickname;

    private String mainCharacterImg;

    private RelationStatus status;
}
