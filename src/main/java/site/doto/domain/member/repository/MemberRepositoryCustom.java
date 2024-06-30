package site.doto.domain.member.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import site.doto.domain.member.entity.Member;

import java.time.LocalDateTime;

public interface MemberRepositoryCustom {
    Slice<Member> findAllByMemberIdAndStatus(@Param("memberId") Long memberId,
                                             @Param("lastFriendId") Long lastFriendId,
                                             @Param("lastFriendLastUpload") LocalDateTime lastFriendLastUpload,
                                             @Param("pageable") Pageable pageable);
}
