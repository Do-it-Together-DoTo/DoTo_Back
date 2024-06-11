package site.doto.domain.chatroom.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomDto {

    Long chatRoomId;

    Long bettingId;

    String bettingName;

    Long memberId;

    String memberNickname;

    String mainCharacterImg;
}
