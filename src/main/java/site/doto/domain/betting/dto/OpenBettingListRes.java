package site.doto.domain.betting.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenBettingListRes {
    List<BettingDetailsRes> openBetting;
}
