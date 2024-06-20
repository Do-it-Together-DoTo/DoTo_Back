package site.doto.domain.relation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationDto {
    private Long memberId;

    private String nickname;

    private String mainCharacterImg;
}
