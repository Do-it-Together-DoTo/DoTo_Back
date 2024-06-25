package site.doto.domain.member_betting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.doto.domain.member_betting.entity.MemberBetting;
import site.doto.domain.member_betting.entity.MemberBettingPK;

import java.util.List;

public interface MemberBettingRepository extends JpaRepository<MemberBetting, MemberBettingPK> {
    Boolean existsByBettingId(Long bettingId);

    Boolean existsByMemberIdAndBettingId(Long memberId, Long bettingId);

    @EntityGraph(attributePaths = "member")
    List<MemberBetting> findByBettingId(Long bettingId);

    @Modifying
    @Query("delete " +
            "from MemberBetting m " +
            "where m.betting.id in " +
            "(select b.id from Betting b " +
            "where b.todo is null " +
            "or b.todo.id in " +
            "(select t.id from Todo t " +
            "where t.date < current_date))")
    void deleteRelatedMemberBetting();

}
