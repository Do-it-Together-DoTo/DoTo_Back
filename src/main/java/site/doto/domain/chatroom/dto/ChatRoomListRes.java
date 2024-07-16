package site.doto.domain.chatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRoomListRes {
    private List<ChatRoomDto> chatRooms;
}
