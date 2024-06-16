package site.doto.domain.record.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.record.entity.Record;
import site.doto.domain.record.entity.RecordPK;

import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, RecordPK>, RecordRepositoryCustom {

    Optional<Record> findByRecordPK(@Param("recordPK") RecordPK recordPK);
}
