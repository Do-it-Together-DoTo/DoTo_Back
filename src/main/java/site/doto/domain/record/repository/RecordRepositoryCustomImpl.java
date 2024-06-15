package site.doto.domain.record.repository;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static site.doto.domain.record.entity.QRecord.record;

@RequiredArgsConstructor
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private Map<String, NumberPath<Integer> > map;

    @PostConstruct
    void init() {
        map = new HashMap<>();
        map.put("coinUsage", record.coinUsage);
        map.put("coinEarned", record.coinEarned);
        map.put("myBetting", record.myBetting);
        map.put("betParticipation", record.betParticipation);
        map.put("betWins", record.betWins);
        map.put("betAmount", record.betAmount);
        map.put("betProfit", record.betProfit);
    }

    @Override
    public void updateRecord(Long memberId, Integer year, Integer month, List<String> fields, List<Integer> variances) {
        JPAUpdateClause query = jpaQueryFactory.update(record);

        for (int i = 0; i < fields.size(); i++) {
            query.set(map.get(fields.get(i)), map.get(fields.get(i)).add(variances.get(i)));
        }

        query.where(record.recordPK.memberId.eq(memberId))
                .where(record.recordPK.year.eq(year))
                .where(record.recordPK.month.eq(month))
                .execute();
    }
}
