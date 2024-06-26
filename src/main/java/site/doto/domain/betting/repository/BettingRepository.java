package site.doto.domain.betting.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import site.doto.domain.betting.entity.Betting;

import java.util.Optional;
import java.util.List;

public interface BettingRepository extends JpaRepository<Betting, Long>, BettingRepositoryCustom {
    @EntityGraph(attributePaths = {"member", "todo"})
    Optional<Betting> findById(Long bettingId);

    @EntityGraph(attributePaths = {"member", "todo", "chatRoom"})
    @Query("select b " +
            "from Betting b " +
            "where b.id = :bettingId")
    Optional<Betting> findByIdWithChatRoom(@Param("bettingId") Long bettingId);

    @Query("select b " +
            "from Betting b " +
            "join b.todo t " +
            "where b.member.id = :memberId " +
            "and t.date >= current_date")
    List<Betting> findAfterToday(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select b " +
            "from Betting b " +
            "join fetch b.member m " +
            "join fetch m.mainCharacter c " +
            "join fetch c.characterType t " +
            "where b.member.id = :memberId")
    List<Betting> findAllByMember(@Param("memberId") Long memberId);

    @Query("select b " +
            "from Betting b " +
            "join fetch b.todo t " +
            "join fetch b.member m " +
            "join fetch m.mainCharacter c " +
            "join fetch c.characterType t " +
            "join MemberBetting mb " +
            "on mb.betting = b " +
            "where mb.member.id = :memberId")
    List<Betting> findJoiningBetting(@Param("memberId") Long memberId);

    @Query("select b " +
            "from Betting b " +
            "join fetch b.todo t " +
            "where t.date < current_date")
    List<Betting> findClosedBetting();

    @Modifying
    @Query("delete " +
            "from Betting b " +
            "where b.isAchieved is not null")
    void deleteClosedBetting();

    @Modifying
    @Query("update " +
            "from Betting b " +
            "set b.isAchieved = :isAchieved " +
            "where b in :betting")
    void updateIsAchieved(@Param("betting") List<Betting> betting, @Param("isAchieved") Boolean isAchieved);

}
