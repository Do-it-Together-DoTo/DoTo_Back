package site.doto.domain.chatroom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.chatroom.dto.ChatRoomDto;
import site.doto.domain.chatroom.dto.ChatRoomListRes;
import site.doto.domain.chatroom.repository.ChatRoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomListRes findChatRooms(Long memberId) {
        List<ChatRoomDto> chatRooms = chatRoomRepository.findChatRoomById(memberId);

        return new ChatRoomListRes(chatRooms);
    }
}
