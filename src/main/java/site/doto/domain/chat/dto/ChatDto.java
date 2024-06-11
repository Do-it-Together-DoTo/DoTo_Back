package site.doto.domain.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatDto {

    Long chatId;

    String contents;

    LocalDateTime createdDate;

    Long memberId;

    String memberNickname;

    String mainCharacterImg;
}
