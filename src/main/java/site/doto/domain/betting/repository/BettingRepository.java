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

    @Query("select b " +
            "from Betting b " +
            "join b.todo t " +
            "where b.member.id = :memberId " +
            "and t.date >= current_date")
    List<Betting> findAfterToday(@Param("memberId") Long memberId, Pageable pageable);

    @EntityGraph(attributePaths = {"member", "todo"})
    @Query("select b " +
            "from Betting b " +
            "where b.member.id = :memberId")
    List<Betting> findAllByMember(@Param("memberId") Long memberId);

    @EntityGraph(attributePaths = {"member", "todo"})
    @Query("select b " +
            "from Betting b " +
            "join MemberBetting mb " +
            "on mb.betting = b " +
            "where mb.member.id = :memberId")
    List<Betting> findJoiningBetting(@Param("memberId") Long memberId);

    @EntityGraph(attributePaths = {"member", "todo"})
    @Query("select b " +
            "from Betting b " +
            "where b.todo.date >= current_date " +
            "and b.member in (" +
            "select m " +
            "from Member m " +
            "join Friend f " +
            "on m.id = f.toMember.id " +
            "where f.fromMember.id = :memberId " +
            "and f.status = site.doto.domain.friend.enums.FriendRelation.ACCEPTED)")
    List<Betting> findOpenBetting(@Param("memberId") Long memberId);
}
