package site.doto.domain.relation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationDetailDto {
    private Long memberId;

    private String nickname;

    private String description;

    private String mainCharacterImg;

    private Integer mainCharacterExp;

    private Integer mainCharacterLevel;
}
