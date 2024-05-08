package site.doto.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.doto.global.logtracer.LogTrace;
import site.doto.global.logtracer.LogTraceAspect;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final LogTrace logTrace;

    @Bean
    @ConditionalOnProperty(value = "logTracer", havingValue = "true")
    public LogTraceAspect logTraceAspect() {
        return new LogTraceAspect(logTrace);
    }
}
