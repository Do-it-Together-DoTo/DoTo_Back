package site.doto.domain.betting.dto;

import lombok.Data;
import site.doto.domain.betting.entity.Betting;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyBettingListRes {
    private List<BettingDto> myBetting = new ArrayList<>();

    private List<BettingDto> joiningBetting = new ArrayList<>();

    private List<BettingDto> closedBetting = new ArrayList<>();

    public MyBettingListRes(List<Betting> myBetting) {
        myBetting.stream()
                .map(BettingDto::toDto)
                .forEach(this.myBetting::add);
    }
}
