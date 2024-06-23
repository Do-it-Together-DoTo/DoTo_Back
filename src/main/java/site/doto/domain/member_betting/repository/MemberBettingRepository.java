package site.doto.domain.member_betting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.member_betting.entity.MemberBetting;
import site.doto.domain.member_betting.entity.MemberBettingPK;

import java.util.List;

public interface MemberBettingRepository extends JpaRepository<MemberBetting, MemberBettingPK> {
    Boolean existsByBettingId(Long bettingId);

    Boolean existsByMemberIdAndBettingId(Long memberId, Long bettingId);

    @EntityGraph(attributePaths = "member")
    List<MemberBetting> findByBettingId(Long bettingId);
}
