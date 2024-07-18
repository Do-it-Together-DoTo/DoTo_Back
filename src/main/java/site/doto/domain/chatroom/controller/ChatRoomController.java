package site.doto.domain.chatroom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.chatroom.dto.*;
import site.doto.domain.chatroom.service.ChatRoomService;
import site.doto.global.dto.ResponseDto;
import site.doto.global.status_code.SuccessCode;

@RestController
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseDto<ChatRoomListRes> chatRoomList() {
        Long memberId = 1L;

        ChatRoomListRes result = chatRoomService.findChatRooms(memberId);

        return ResponseDto.success(SuccessCode.CHATROOMS_INQUIRY_OK, result);
    }

    @PostMapping("/{chatRoomId}")
    public ResponseDto<?> chatRoomJoin(
            @PathVariable Long chatRoomId) {
        return ResponseDto.success(SuccessCode.MEMBER_CHATROOM_CREATED, null);
    }
}
