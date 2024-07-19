package site.doto.domain.chatroom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.chatroom.dto.ChatRoomDto;
import site.doto.domain.chatroom.dto.ChatRoomListRes;
import site.doto.domain.chatroom.entity.ChatRoom;
import site.doto.domain.chatroom.repository.ChatRoomRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.domain.member_chat_room.entity.MemberChatRoom;
import site.doto.domain.member_chat_room.entity.MemberChatRoomPK;
import site.doto.domain.member_chat_room.repository.MemberChatRoomRepository;
import site.doto.global.exception.CustomException;

import java.util.List;
import java.util.Optional;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;

    @Transactional(readOnly = true)
    public ChatRoomListRes findChatRooms(Long memberId) {
        List<ChatRoomDto> chatRooms = chatRoomRepository.findChatRoomById(memberId);

        return new ChatRoomListRes(chatRooms);
    }

    public void joinChatRoom(Long memberId, Long chatRoomId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(CHATROOM_NOT_FOUND));

        MemberChatRoomPK memberChatRoomPK = new MemberChatRoomPK(memberId, chatRoomId);

        Optional<MemberChatRoom> memberChatRoom = memberChatRoomRepository.findById(memberChatRoomPK);

        if (memberChatRoom.isPresent()) {
            throw new CustomException(CHATROOM_ALREADY_JOINING);
        }

        memberChatRoomRepository.save(new MemberChatRoom(memberChatRoomPK, member, chatRoom));
    }
}
