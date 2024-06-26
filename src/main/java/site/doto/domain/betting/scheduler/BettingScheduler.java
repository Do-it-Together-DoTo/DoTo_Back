package site.doto.domain.betting.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.doto.domain.betting.service.BettingService;

@Component
@RequiredArgsConstructor
public class BettingScheduler {
    private final BettingService bettingService;

    @Scheduled(cron = "0 30 23 * * *")
    public void deleteFinishedBetting() {
        bettingService.deleteClosedBetting();
    }

    @Scheduled(cron = "5 0 0 * * *")
    public void closeBetting() {
        bettingService.closeBetting();
    }
}
