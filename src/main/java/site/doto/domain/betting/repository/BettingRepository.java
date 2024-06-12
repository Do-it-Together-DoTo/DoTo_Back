package site.doto.domain.betting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.betting.entity.Betting;

import java.util.Optional;

public interface BettingRepository extends JpaRepository<Betting, Long> {
    Optional<Betting> findById(Long bettingId);
}
