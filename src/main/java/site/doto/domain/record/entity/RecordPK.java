package site.doto.domain.record.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordPK implements Serializable {
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "record_year")
    private int year;

    @Column(name = "record_month")
    private int month;
}
