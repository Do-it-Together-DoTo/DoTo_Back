package site.doto.domain.member_betting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.member.entity.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberBetting {
    @EmbeddedId
    private MemberBettingPK memberBettingPK;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @MapsId("bettingId")
    @JoinColumn(name = "betting_id")
    private Betting betting;

    private Integer cost;

    private Boolean prediction;
}
