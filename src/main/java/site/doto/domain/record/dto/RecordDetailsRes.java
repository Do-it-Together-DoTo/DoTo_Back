package site.doto.domain.record.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.record.entity.Record;
import site.doto.domain.todo.dto.TodoRecordDto;

import java.util.List;

@Data
@Builder
public class RecordDetailsRes {
    private Integer year;

    private Integer month;

    private Integer coinUsage;

    private Integer coinEarned;

    private Integer betParticipation;

    private Integer betWins;

    private Integer betAmount;

    private Integer betProfit;

    private Integer myBetOpen;

    private List<TodoRecordDto> todoRecords;

    public static RecordDetailsRes toDto(Record record, Integer openCount, List<TodoRecordDto> records) {
        return RecordDetailsRes.builder()
                .year(record.getYear())
                .month(record.getMonth())
                .coinUsage(record.getCoinUsage())
                .coinEarned(record.getCoinEarned())
                .betParticipation(record.getBetParticipation())
                .betWins(record.getBetWins())
                .betAmount(record.getBetAmount())
                .betProfit(record.getBetProfit())
                .myBetOpen(openCount)
                .todoRecords(records)
                .build();
    }
}
