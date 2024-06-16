package site.doto.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Configuration
@ConditionalOnProperty(value = "spring.redis.embedded", havingValue = "true")
public class EmbeddedRedisConfig {
    @Value("${spring.redis.port}")
    public int port;

    private RedisServer redisServer;

    @PostConstruct
    public void postConstruct() throws IOException {
        if (isArmMac()) {
            redisServer = new RedisServer(getRedisFileForArcMac(), port);
        } else {
            redisServer = RedisServer.builder()
                    .port(port)
                    .setting("maxmemory 128M")
                    .build();
        }

        try {
            redisServer.start();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void preDestroy() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isArmMac() {
        return Objects.equals(System.getProperty("os.arch"), "aarch64")
                && Objects.equals(System.getProperty("os.name"), "Mac OS X");
    }

    /**
     * ARM 아키텍처를 사용하는 Mac에서 실행할 수 있는 Redis 바이너리 파일을 반환
     */
    private File getRedisFileForArcMac() {
        try {
            return new ClassPathResource("redis/redis-server-7.2.3-mac-arm64").getFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
