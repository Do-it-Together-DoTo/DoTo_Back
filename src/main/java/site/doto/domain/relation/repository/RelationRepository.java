package site.doto.domain.relation.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.doto.domain.relation.entity.Relation;
import site.doto.domain.relation.entity.RelationPK;
import site.doto.domain.relation.enums.RelationStatus;

public interface RelationRepository extends JpaRepository<Relation, RelationPK> {
    @Modifying
    @Query("update Relation r set r.status = :status where r.relationPK = :relationPK")
    void updateRelationStatus(@Param("relationPK") RelationPK relationPK, @Param("status") RelationStatus status);
}
