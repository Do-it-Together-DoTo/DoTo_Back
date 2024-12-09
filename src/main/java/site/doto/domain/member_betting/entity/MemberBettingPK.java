package site.doto.domain.member_betting.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberBettingPK implements Serializable {
    private Long memberId;

    private Long bettingId;
}