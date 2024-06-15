package site.doto.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@ConditionalOnProperty(value = "spring.redis.embedded", havingValue = "true")
public class EmbeddedRedisConfig {
    @Value("${spring.redis.port}")
    public int port;

    private RedisServer redisServer;

    @PostConstruct
    public void postConstruct() throws IOException {
        this.redisServer = RedisServer.builder()
                .port(port)
                .setting("maxmemory 128M")
                .build();
        try {
            redisServer.start();
        } catch (RuntimeException e) {
        }

    }

    @PreDestroy
    public void preDestroy() throws IOException {
        redisServer.stop();
    }
}

