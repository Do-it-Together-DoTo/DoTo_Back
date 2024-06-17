package site.doto.domain.betting.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.doto.domain.betting.entity.Betting;

import java.util.Optional;
import java.util.List;

public interface BettingRepository extends JpaRepository<Betting, Long> {
    @EntityGraph(attributePaths = {"member", "todo"})
    Optional<Betting> findById(Long bettingId);

    @Query("select b from Betting b join b.todo t " +
            "where b.member.id = :memberId and t.date >= current_date")
    List<Betting> findAfterToday(@Param("memberId") Long memberId, Pageable pageable);

}
