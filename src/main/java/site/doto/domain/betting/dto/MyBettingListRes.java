package site.doto.domain.betting.dto;

import lombok.Data;

import java.util.List;

@Data
public class MyBettingListRes {

    BettingDto myBetting;

    List<BettingDto> joiningBetting;

    List<BettingDto> closedBetting;
}
