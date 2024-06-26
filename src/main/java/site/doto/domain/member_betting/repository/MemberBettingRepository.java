package site.doto.domain.member_betting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.member_betting.entity.MemberBetting;
import site.doto.domain.member_betting.entity.MemberBettingPK;

import java.util.List;

public interface MemberBettingRepository extends JpaRepository<MemberBetting, MemberBettingPK> {
    Boolean existsByBettingId(Long bettingId);

    Boolean existsByMemberIdAndBettingId(Long memberId, Long bettingId);

    @EntityGraph(attributePaths = "member")
    List<MemberBetting> findByBettingId(Long bettingId);

    @EntityGraph(attributePaths = "member")
    @Query("select mb " +
            "from MemberBetting mb " +
            "where mb.betting in :finishedBetting")
    List<MemberBetting> findClosedMemberBetting(@Param("finishedBetting")List<Betting> finishedBetting);


    @Modifying
    @Query("delete " +
            "from MemberBetting m " +
            "where m.betting in " +
            "(select b " +
            "from Betting b " +
            "where b.isAchieved is not null)")
    void deleteRelatedMemberBetting();

}
