package site.doto.domain.betting.dto;

import lombok.Data;
import site.doto.domain.betting.entity.Betting;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OpenBettingListRes {
    private List<BettingDto> openBetting;

    public OpenBettingListRes(List<Betting> openBetting) {
        this.openBetting = openBetting.stream()
                .map(BettingDto::toDto)
                .collect(Collectors.toList());
    }
}
