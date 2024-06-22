package site.doto.domain.betting.dto;

import lombok.Data;
import site.doto.domain.betting.entity.Betting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class MyBettingListRes {

    private List<BettingDto> myBetting = new ArrayList<>();

    private List<BettingDto> joiningBetting = new ArrayList<>();

    private List<BettingDto> closedBetting = new ArrayList<>();

    public MyBettingListRes(List<Betting> myBetting, List<Betting> bettingList) {
        myBetting.stream()
                .map(BettingDto::toDto)
                .forEach(this.myBetting::add);

        bettingList.stream()
                .forEach(betting -> {
                    List<BettingDto> list =
                            betting.getDate().isBefore(LocalDate.now()) ? closedBetting : joiningBetting;
                    list.add(BettingDto.toDto(betting));
                });
    }
}
