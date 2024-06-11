package site.doto.domain.chatroom.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.chatroom.dto.*;
import site.doto.global.dto.ResponseDto;
import site.doto.global.status_code.SuccessCode;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatting")
public class ChatRoomController {
    @GetMapping
    public ResponseDto<ChatRoomListRes> chatRoomList() {
        ChatRoomListRes result = new ChatRoomListRes();

        List<ChatRoomDto> chatRooms = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            chatRooms.add(ChatRoomDto.builder()
                    .chatRoomId(30001L + i)
                    .bettingId(10001L + i)
                    .bettingName("베팅 이름" + i)
                    .memberId(1L + i)
                    .memberNickname("닉네임" + i)
                    .mainCharacterImg("이미지 주소" + i)
                    .build());
        }

        result.setChatRooms(chatRooms);

        return ResponseDto.success(SuccessCode.CHATROOMS_INQUIRY_OK, result);
    }

    @PostMapping("/{chatRoomId}")
    public ResponseDto<?> chatRoomJoin(
            @PathVariable Long chatRoomId) {
        return ResponseDto.success(SuccessCode.MEMBER_CHATROOM_CREATED, null);
    }
}
