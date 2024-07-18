package site.doto.domain.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomDto {
    private Long chatRoomId;

    private Long bettingId;

    private String bettingName;

    private Long memberId;

    private String memberNickname;

    private String mainCharacterImg;
}
