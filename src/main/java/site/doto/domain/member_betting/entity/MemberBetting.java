package site.doto.domain.member_betting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.member.entity.Member;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberBetting implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Betting betting;

    private int cost;

    private boolean prediction;

}
