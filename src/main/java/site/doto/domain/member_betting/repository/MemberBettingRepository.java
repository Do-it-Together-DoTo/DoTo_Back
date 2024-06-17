package site.doto.domain.member_betting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.member_betting.entity.MemberBetting;
import site.doto.domain.member_betting.entity.MemberBettingPK;

public interface MemberBettingRepository extends JpaRepository<MemberBetting, MemberBettingPK> {
    boolean existsByBettingId(Long bettingId);
}
