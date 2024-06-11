package site.doto.domain.chatroom.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.chatroom.dto.*;
import site.doto.global.dto.ResponseDto;
import site.doto.global.status_code.SuccessCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatting")
public class ChattingController {
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

    @GetMapping("/{chatRoomId}")
    public ResponseDto<ChatListRes> chatList(
            @PathVariable Long chatRoomId,
            @ModelAttribute ChatListReq chatListReq,
            @PageableDefault(size = 20) Pageable pageable) {

        List<ChatDto> chats = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            chats.add(ChatDto.builder()
                    .chatId(40000L + i)
                    .contents("메세지" + i)
                    .createdDate(LocalDateTime.now())
                    .memberId((0L + i) % 5)
                    .memberNickname("닉네임" + (0L + i) % 5)
                    .mainCharacterImg("이미지 주소" + (0L + i) % 5)
                    .build());
        }

        SliceImpl<ChatDto> slice = new SliceImpl<>(chats, pageable, true);

        ChatListRes result = new ChatListRes(slice);

        return ResponseDto.success(SuccessCode.CHATS_INQUIRY_OK, result);
    }

    @PostMapping("/messages/{chatRoomId}")
    public ResponseDto<?> chatAdd(@RequestBody ChatAddReq chatAddReq) {
        return ResponseDto.success(SuccessCode.CHAT_CREATED, null);
    }
}
