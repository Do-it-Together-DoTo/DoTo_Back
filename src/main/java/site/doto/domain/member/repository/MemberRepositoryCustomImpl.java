package site.doto.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import site.doto.domain.member.entity.Member;
import site.doto.domain.relation.dto.RelationListReq;

import java.time.LocalDateTime;
import java.util.List;

import static site.doto.domain.character.entity.QCharacter.character;
import static site.doto.domain.character.entity.QCharacterType.characterType;
import static site.doto.domain.relation.entity.QRelation.relation;
import static site.doto.domain.member.entity.QMember.member;
import static site.doto.domain.relation.enums.RelationStatus.ACCEPTED;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Member> findAllByMemberIdAndStatus(Long memberId, RelationListReq relationListReq, Pageable pageable) {
        JPQLQuery<Member> subQuery = JPAExpressions.select(member)
                .from(member)
                .join(relation).on(member.id.eq(relation.member.id))
                .where(relation.friend.id.eq(memberId)
                        .and(relation.status.eq(ACCEPTED)));

        List<Member> members = queryFactory.selectFrom(member)
                .leftJoin(member.mainCharacter, character).fetchJoin()
                .leftJoin(character.characterType, characterType).fetchJoin()
                .where(member.in(subQuery))
                .where(ltLastUpload(relationListReq))
                .orderBy(member.lastUpload.desc())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(members, pageable, members.size() == pageable.getPageSize());
    }

    private BooleanExpression ltLastUpload(RelationListReq relationListReq) {
        LocalDateTime lastUpload = relationListReq.getLastFriendLastUpload();

        return lastUpload == null ? null : member.lastUpload.lt(lastUpload);
    }
}
