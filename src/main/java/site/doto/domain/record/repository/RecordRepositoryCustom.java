package site.doto.domain.record.repository;

import java.util.List;

public interface RecordRepositoryCustom {
    void updateRecord(Long memberId, Integer year, Integer month, List<String> fields, List<Integer> variances);
}
