package site.doto.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatDto {
    private Long chatId;

    private String contents;

    private LocalDateTime createdDate;

    private Long memberId;

    private String memberNickname;

    private String mainCharacterImg;
}
