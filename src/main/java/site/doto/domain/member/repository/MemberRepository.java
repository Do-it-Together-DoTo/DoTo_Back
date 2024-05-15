package site.doto.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(long memberId);
}
