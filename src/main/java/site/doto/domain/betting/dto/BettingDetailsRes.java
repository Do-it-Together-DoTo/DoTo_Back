package site.doto.domain.betting.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BettingDetailsRes {
    Long bettingId;

    String bettingName;

    Long memberId;

    String memberNickname;

    String todoContents;

    Integer successCoins;

    Integer failureCoins;

    Integer participantCount;

    Boolean isParticipating;

    Long chatRoomId;
}
