package site.doto;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import site.doto.global.redis.RedisUtils;

public abstract class BaseTest {
    @Autowired
    private RedisUtils redisUtils;

    @BeforeEach
    public void flushRedis() {
        redisUtils.flushRedis();
    }
}
