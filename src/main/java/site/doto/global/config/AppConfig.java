package site.doto.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.doto.global.logtracer.LogTrace;
import site.doto.global.logtracer.LogTraceAspect;
import site.doto.global.p6spy.P6spySqlFormatConfiguration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final LogTrace logTrace;

    @Bean
    @ConditionalOnProperty(value = "logTracer", havingValue = "true")
    public LogTraceAspect logTraceAspect() {
        return new LogTraceAspect(logTrace);
    }

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6spySqlFormatConfiguration.class.getName());
    }
}