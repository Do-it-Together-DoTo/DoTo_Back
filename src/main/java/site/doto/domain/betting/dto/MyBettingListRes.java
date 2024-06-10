package site.doto.domain.betting.dto;

import lombok.Data;

import java.util.List;

@Data
public class MyBettingListRes {

    BettingDetailsRes myBetting;

    List<BettingDetailsRes> joiningBetting;

    List<BettingDetailsRes> closedBetting;
}
