package site.doto.domain.betting.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.JPAExpressions;
import lombok.RequiredArgsConstructor;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.friend.enums.FriendRelation;
import site.doto.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

import static site.doto.domain.betting.entity.QBetting.betting;
import static site.doto.domain.friend.entity.QFriend.friend;
import static site.doto.domain.member.entity.QMember.member;
import static site.doto.domain.member_betting.entity.QMemberBetting.memberBetting;

@RequiredArgsConstructor
public class BettingRepositoryCustomImpl implements BettingRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public List<Betting> findOpenBetting(Long memberId) {

        JPQLQuery<Member> subQuery = JPAExpressions.select(member)
                .from(member)
                .join(friend).on(member.id.eq(friend.toMember.id))
                .where(friend.fromMember.id.eq(memberId)
                        .and(friend.status.eq(FriendRelation.ACCEPTED)));

        return queryFactory.selectFrom(betting)
                .leftJoin(betting.member, member).fetchJoin()
                .where(betting.todo.date.goe(LocalDate.now()))
                .where(betting.member.in(subQuery))
                .where(JPAExpressions
                        .selectFrom(memberBetting)
                        .where(memberBetting.betting.id.eq(betting.id))
                        .where(memberBetting.member.id.eq(memberId))
                        .notExists())
                .fetch();
    }
}
