package site.doto.domain.betting.dto;

import lombok.Data;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member_betting.entity.MemberBetting;
import site.doto.domain.member_betting.entity.MemberBettingPK;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BettingJoinReq {
    @NotNull
    @Max(value = 50)
    @Min(value = 10)
    private Integer cost;

    @NotNull
    private Boolean prediction;

    public MemberBetting toEntity(Member member, Betting betting) {
        return MemberBetting.builder()
                .memberBettingPK(new MemberBettingPK(member.getId(), betting.getId()))
                .member(member)
                .betting(betting)
                .cost(cost)
                .prediction(prediction)
                .build();
    }
}
