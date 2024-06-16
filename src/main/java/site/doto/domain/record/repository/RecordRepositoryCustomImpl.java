package site.doto.domain.record.repository;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import site.doto.domain.record.dto.RecordUpdateDto;

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
        map.put("myBetOpen", record.myBetOpen);
        map.put("betParticipation", record.betParticipation);
        map.put("betWins", record.betWins);
        map.put("betAmount", record.betAmount);
        map.put("betProfit", record.betProfit);
    }

    @Override
    public void updateRecord(RecordUpdateDto update) {
        JPAUpdateClause query = jpaQueryFactory.update(record);

        List<String> fields = update.getFields();
        List<Integer> variances = update.getVariances();

        for (int i = 0; i < fields.size(); i++) {
            query.set(map.get(fields.get(i)), map.get(fields.get(i)).add(variances.get(i)));
        }

        query.where(record.recordPK.memberId.eq(update.getMemberId()))
                .where(record.recordPK.year.eq(update.getYear()))
                .where(record.recordPK.month.eq(update.getMonth()))
                .execute();
    }
}
