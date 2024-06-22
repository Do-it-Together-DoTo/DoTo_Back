package site.doto.domain.chatroom.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomListRes {
    private List<ChatRoomDto> chatRooms;
}
