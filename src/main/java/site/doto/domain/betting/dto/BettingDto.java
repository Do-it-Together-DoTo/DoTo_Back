package site.doto.domain.betting.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.betting.entity.Betting;

@Data
@Builder
public class BettingDto {
    Long bettingId;

    String bettingName;

    Long memberId;

    String memberNickname;

    String mainCharacterImg;

    static public BettingDto toDto(Betting betting) {
        return BettingDto.builder()
                .bettingId(betting.getId())
                .bettingName(betting.getName())
                .memberId(betting.getMember().getId())
                .memberNickname(betting.getMember().getNickname())
                .mainCharacterImg(betting.getMember().getMainCharacterImg())
                .build();
    }
}
