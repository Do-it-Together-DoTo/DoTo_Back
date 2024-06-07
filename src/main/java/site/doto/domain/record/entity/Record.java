package site.doto.domain.record.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Id
    private Member member;

    @Id
    private Integer year;

    @Id
    private Integer month;

    private Integer coinUsage;

    private Integer coinEarned;

    private Integer betParticipation;

    private Integer betWins;

    private Integer betAmount;

    private Integer betProfit;

}
