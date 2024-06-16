package site.doto.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.record.entity.Record;
import site.doto.domain.record.entity.RecordPK;

public interface RecordRepository extends JpaRepository<Record, RecordPK>, RecordRepositoryCustom {

    Boolean existsByRecordPK(RecordPK recordPK);
}
