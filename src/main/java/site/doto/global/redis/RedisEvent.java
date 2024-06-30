package site.doto.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class RedisEvent {
    private final RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        redisUtils.updateRecordToDB();
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void schedule() {
        redisUtils.updateRecordToDB();
    }
}
