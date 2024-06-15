package site.doto.domain.record.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Serializable {
    @EmbeddedId
    private RecordPK recordPK;

    private Integer coinUsage;

    private Integer coinEarned;

    private Integer myBetting;

    private Integer betParticipation;

    private Integer betWins;

    private Integer betAmount;

    private Integer betProfit;

    public Long getMemberId() {
        return recordPK.getMemberId();
    }

    public Integer getYear() {
        return recordPK.getYear();
    }

    public Integer getMonth() {
        return recordPK.getMonth();
    }
}
