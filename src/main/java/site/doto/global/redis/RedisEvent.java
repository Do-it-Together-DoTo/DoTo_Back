package site.doto.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class RedisEvent {
    private final RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        redisUtils.flushRedis();
    }
}
