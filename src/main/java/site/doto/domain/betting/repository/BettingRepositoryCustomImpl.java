package site.doto.domain.betting.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.JPAExpressions;
import lombok.RequiredArgsConstructor;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.category.entity.Category;
import site.doto.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

import static site.doto.domain.betting.entity.QBetting.betting;
import static site.doto.domain.character.entity.QCharacter.character;
import static site.doto.domain.character.entity.QCharacterType.characterType;
import static site.doto.domain.relation.entity.QRelation.relation;
import static site.doto.domain.member.entity.QMember.member;
import static site.doto.domain.member_betting.entity.QMemberBetting.memberBetting;
import static site.doto.domain.relation.enums.RelationStatus.ACCEPTED;
import static site.doto.domain.todo.entity.QTodo.todo;

@RequiredArgsConstructor
public class BettingRepositoryCustomImpl implements BettingRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public List<Betting> findOpenBetting(Long memberId) {
        JPQLQuery<Member> subQuery = JPAExpressions.select(member)
                .from(member)
                .join(relation).on(member.id.eq(relation.member.id))
                .where(relation.friend.id.eq(memberId)
                        .and(relation.status.eq(ACCEPTED)));

        return jpaQueryFactory.selectFrom(betting)
                .leftJoin(betting.member, member).fetchJoin()
                .leftJoin(member.mainCharacter, character).fetchJoin()
                .leftJoin(character.characterType, characterType).fetchJoin()
                .where(betting.todo.date.goe(LocalDate.now()))
                .where(betting.member.in(subQuery))
                .where(JPAExpressions
                        .selectFrom(memberBetting)
                        .where(memberBetting.betting.id.eq(betting.id))
                        .where(memberBetting.member.id.eq(memberId))
                        .notExists())
                .fetch();
    }

    @Override
    public List<Betting> findBettingsByCategory(Category category) {
        return jpaQueryFactory.selectFrom(betting)
                .innerJoin(betting.todo, todo).fetchJoin()
                .where(todo.category.id.eq(category.getId()))
                .fetch();
    }
}
