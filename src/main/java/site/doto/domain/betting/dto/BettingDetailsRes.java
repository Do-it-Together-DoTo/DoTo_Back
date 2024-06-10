package site.doto.domain.betting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BettingDetailsRes {
    Long memberId;

    String mainCharacterImg;

    String memberNickname;

    Long bettingId;

    String bettingName;
}
