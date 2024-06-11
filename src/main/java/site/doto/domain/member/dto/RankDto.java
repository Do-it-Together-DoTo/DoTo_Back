package site.doto.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankDto {
    Long memberId;

    String memberNickname;

    String mainCharacterImg;

    Integer score;

    Integer rank;
}
