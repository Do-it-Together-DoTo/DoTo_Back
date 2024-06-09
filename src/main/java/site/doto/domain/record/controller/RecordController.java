package site.doto.domain.record.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.doto.domain.record.dto.RecordDetailsRes;
import site.doto.domain.todo.dto.TodoRecordDto;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.RECORDS_INQUIRY_OK;

@RestController
public class RecordController {
    @GetMapping("/records")
    public ResponseDto<RecordDetailsRes> recordDetails(
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {
        List<TodoRecordDto> records = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            records.add(TodoRecordDto.builder()
                    .name("카테고리")
                    .total(20+i)
                    .achieved(10+i)
                    .build());
        }

        RecordDetailsRes result = RecordDetailsRes.builder()
                .year(year)
                .month(month)
                .coinUsage(2000)
                .coinEarned(200)
                .betAmount(10)
                .betParticipation(3)
                .betWins(2)
                .betProfit(100)
                .myBetOpen(3)
                .todoRecords(records)
                .build();

        return ResponseDto.success(RECORDS_INQUIRY_OK, result);
    }
}
