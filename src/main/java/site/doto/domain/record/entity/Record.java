package site.doto.domain.record.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.member.entity.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @Column(name = "record_year")
    private Integer year;

    @Id
    @Column(name = "record_month")
  
    private Integer month;

    private Integer coinUsage;

    private Integer coinEarned;

    private Integer betParticipation;

    private Integer betWins;

    private Integer betAmount;

    private Integer betProfit;

}
