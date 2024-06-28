package site.doto.domain.member.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import site.doto.domain.member.entity.Member;
import site.doto.domain.relation.dto.RelationListReq;

public interface MemberRepositoryCustom {
    Slice<Member> findAllByMemberIdAndStatus(@Param("memberId") Long memberId,
                                             @Param("relationListReq") RelationListReq relationListReq,
                                             @Param("pageable") Pageable pageable);
}
