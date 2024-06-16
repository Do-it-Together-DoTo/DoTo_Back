package site.doto.domain.record.dto;

import lombok.Getter;
import site.doto.domain.record.entity.RecordPK;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecordUpdateDto {
    private RecordPK recordPK;

    private List<String> fields;

    private List<Integer> variances;

    public RecordUpdateDto(String[] pk) {
        recordPK = new RecordPK(Long.parseLong(pk[1]), Integer.parseInt(pk[2]), Integer.parseInt(pk[3]));
        fields = new ArrayList<>();
        variances = new ArrayList<>();
    }

    public void addData(String field, Integer variance) {
        fields.add(field);
        variances.add(variance);
    }

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
