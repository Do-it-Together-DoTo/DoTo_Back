package site.doto.domain.record.repository;

import site.doto.domain.record.dto.RecordUpdateDto;

public interface RecordRepositoryCustom {
    void updateRecord(RecordUpdateDto update);
}
