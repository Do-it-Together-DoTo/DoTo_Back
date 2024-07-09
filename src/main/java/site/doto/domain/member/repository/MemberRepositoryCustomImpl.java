package site.doto.domain.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import site.doto.domain.member.dto.MemberDto;
import site.doto.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

import static site.doto.domain.character.entity.QCharacter.character;
import static site.doto.domain.character.entity.QCharacterType.characterType;
import static site.doto.domain.relation.entity.QRelation.relation;
import static site.doto.domain.member.entity.QMember.member;
import static site.doto.domain.relation.enums.RelationStatus.ACCEPTED;
import static site.doto.domain.relation.enums.RelationStatus.BLOCKED;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Member> findAllByMemberIdAndStatusAccepted(Long memberId, Long lastFriendId, LocalDateTime lastFriendLastUpload, Pageable pageable) {
        JPQLQuery<Member> subQuery = JPAExpressions.select(member)
                .from(member)
                .join(relation).on(member.id.eq(relation.member.id))
                .where(relation.friend.id.eq(memberId)
                        .and(relation.status.eq(ACCEPTED)));

        List<Member> members = queryFactory.selectFrom(member)
                .leftJoin(member.mainCharacter, character).fetchJoin()
                .leftJoin(character.characterType, characterType).fetchJoin()
                .where(member.in(subQuery))
                .where(conditionIdAndDate(lastFriendId, lastFriendLastUpload))
                .orderBy(member.lastUpload.desc())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(members, pageable, members.size() == pageable.getPageSize());
    }

    @Override
    public Slice<Member> findAllByMemberIdAndStatusBlocked(Long memberId, Long lastFriendId, Pageable pageable) {
        JPQLQuery<Member> subQuery = JPAExpressions.select(member)
                .from(member)
                .join(relation).on(member.id.eq(relation.friend.id))
                .where(relation.member.id.eq(memberId)
                        .and(relation.status.eq(BLOCKED)));

        List<Member> members = queryFactory.selectFrom(member)
                .leftJoin(member.mainCharacter, character).fetchJoin()
                .leftJoin(character.characterType, characterType).fetchJoin()
                .where(member.in(subQuery))
                .where(conditionId(lastFriendId))
                .orderBy(member.id.asc())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(members, pageable, members.size() == pageable.getPageSize());
    }

    @Override
    public Slice<MemberDto> findAllBySearchWord(Long memberId, String searchWord, Long lastMemberId, Pageable pageable) {
        JPQLQuery<Member> blocked = JPAExpressions
                .select(relation.member)
                .from(relation)
                .where(relation.friend.id.eq(memberId))
                .where(relation.status.eq(BLOCKED));

        JPQLQuery<Member> blocking = JPAExpressions
                .select(relation.friend)
                .from(relation)
                .where(relation.member.id.eq(memberId))
                .where(relation.status.eq(BLOCKED));

        List<MemberDto> members = queryFactory.select(Projections.constructor(
                MemberDto.class, member.id, member.nickname, characterType.img, relation.status))
                .from(member)
                .leftJoin(member.mainCharacter, character)
                .leftJoin(character.characterType, characterType)
                .leftJoin(relation).on(member.id.eq(relation.friend.id))
                .where(member.id.ne(memberId))
                .where(member.nickname.contains(searchWord))
                .where(member.notIn(blocked))
                .where(member.notIn(blocking))
                .where(conditionId(lastMemberId))
                .orderBy(member.id.asc())
                .limit(pageable.getPageSize())
                .fetch();

        return new SliceImpl<>(members, pageable, members.size() == pageable.getPageSize());
    }

    private BooleanExpression conditionIdAndDate(Long id, LocalDateTime date) {
        if(date == null && id == null) {
            return null;
        }

        return member.lastUpload.lt(date)
                .or(member.lastUpload.eq(date).and(member.id.lt(id)));
    }

    private BooleanExpression conditionId(Long id) {
        return id == null ? null : member.id.lt(id);
    }
}
