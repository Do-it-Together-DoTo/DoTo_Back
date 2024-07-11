package site.doto.domain.member.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import site.doto.domain.member.dto.MemberSearchDto;
import site.doto.domain.member.entity.Member;

import java.time.LocalDateTime;

public interface MemberRepositoryCustom {
    Slice<Member> findAllByMemberIdAndStatusAccepted(@Param("memberId") Long memberId,
                                                     @Param("lastFriendId") Long lastFriendId,
                                                     @Param("lastFriendLastUpload") LocalDateTime lastFriendLastUpload,
                                                     @Param("pageable") Pageable pageable);

    Slice<Member> findAllByMemberIdAndStatusBlocked(@Param("memberId") Long memberId,
                                                     @Param("lastFriendId") Long lastFriendId,
                                                     @Param("pageable") Pageable pageable);

    Slice<MemberSearchDto> findAllBySearchWord(@Param("memberId") Long memberId,
                                               @Param("searchWord") String searchWord,
                                               @Param("lastMemberId") Long lastMemberId,
                                               @Param("pageable") Pageable pageable);
}
