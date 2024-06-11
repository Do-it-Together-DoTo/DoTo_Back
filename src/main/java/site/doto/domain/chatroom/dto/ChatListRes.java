package site.doto.domain.chatroom.dto;

import lombok.Data;
import org.springframework.data.domain.Slice;
import site.doto.global.dto.SliceDto;

@Data
public class ChatListRes {
    SliceDto<ChatDto> chats;

    public ChatListRes(Slice<ChatDto> chats) {
        this.chats = new SliceDto<>(chats);
    }
}
