package site.doto.domain.member.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import site.doto.domain.member.entity.Member;
import site.doto.domain.relation.dto.RelationListReq;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findById(Long memberId);

    @Modifying
    @Query("update Member m set m.coin = m.coin + :coin where m.id = :memberId")
    void updateCoin(@Param("memberId") Long memberId, @Param("coin") Integer coin);
}
