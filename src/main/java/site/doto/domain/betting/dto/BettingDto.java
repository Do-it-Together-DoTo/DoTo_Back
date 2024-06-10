package site.doto.domain.betting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BettingDto {
    Long bettingId;

    String bettingName;

    Long memberId;

    String memberNickname;

    String mainCharacterImg;
}
