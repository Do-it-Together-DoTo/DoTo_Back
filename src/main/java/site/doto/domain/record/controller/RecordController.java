package site.doto.domain.record.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.doto.domain.record.dto.RecordDetailsRes;
import site.doto.global.dto.ResponseDto;

import static site.doto.global.status_code.SuccessCode.RECORDS_INQUIRY_OK;

@RestController
public class RecordController {
    @GetMapping("/records")
    public ResponseDto<RecordDetailsRes> recordDetails(
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {
        RecordDetailsRes result = RecordDetailsRes.builder()
                .year(year)
                .month(month)
                .coinUsage(2000)
                .coinEarned(200)
                .betAmount(10)
                .betParticipation(3)
                .betWins(2)
                .betProfit(100)
                .build();

        return ResponseDto.success(RECORDS_INQUIRY_OK, result);
    }
}
