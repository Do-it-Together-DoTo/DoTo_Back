package site.doto.domain.record.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.record.dto.RecordDetailsRes;
import site.doto.domain.record.repository.RecordRepository;
import site.doto.global.redis.RedisUtils;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {
    private RecordRepository recordRepository;
    private RedisUtils redisUtils;

    public void setRedisDataToRes(Long memberId, Integer year, Integer month, RecordDetailsRes dto) {
        Set<String> keys = redisUtils.findKeys("record:" + memberId + ":" + year + ":" + month + "*");

        for (String key : keys) {
            String[] split = key.split("::");

            String pk = split[0];
            String field = split[1];
            Integer variance = (Integer) redisUtils.getData(key);

            switch (field) {
                case "coinUsage":
                    dto.setCoinUsage(dto.getCoinUsage() + variance);
                    break;
                case "coinEarned":
                    dto.setCoinEarned(dto.getCoinEarned() + variance);
                    break;
                case "myBetOpen":
                    dto.setMyBetOpen(dto.getMyBetOpen() + variance);
                    break;
                case "betParticipation":
                    dto.setBetParticipation(dto.getBetParticipation() + variance);
                    break;
                case "betWins":
                    dto.setBetWins(dto.getBetWins() + variance);
                    break;
                case "betAmount":
                    dto.setBetAmount(dto.getBetAmount() + variance);
                    break;
                case "betProfit":
                    dto.setBetProfit(dto.getBetProfit() + variance);
            }
        }
    }
}
