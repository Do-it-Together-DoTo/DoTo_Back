package site.doto.domain.chat.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.chat.dto.ChatAddReq;
import site.doto.domain.chat.dto.ChatDto;
import site.doto.domain.chat.dto.ChatListReq;
import site.doto.domain.chat.dto.ChatListRes;
import site.doto.global.dto.ResponseDto;
import site.doto.global.status_code.SuccessCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chatting")
public class ChatController {
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
                    .memberId((4L + i) % 5 + 1)
                    .memberNickname("닉네임" + ((4L + i) % 5 + 1))
                    .mainCharacterImg("이미지 주소" + ((4L + i) % 5 + 1))
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
