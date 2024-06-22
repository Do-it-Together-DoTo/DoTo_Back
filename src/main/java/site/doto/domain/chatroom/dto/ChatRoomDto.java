package site.doto.domain.chatroom.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomDto {
    private Long chatRoomId;

    private Long bettingId;

    private String bettingName;

    private Long memberId;

    private String memberNickname;

    private String mainCharacterImg;
}
