package site.doto.global.status_code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    EMAIL_SEND_OK(200, "이메일 인증번호 발송 성공"),
    EMAIL_AUTH_OK(200, "이메일 인증번호 확인 성공"),
    LOGIN_OK(200, "로그인 성공"),
    MEMBER_MODIFY_OK(200, "회원 정보 수정 성공"),
    PWD_MODIFY_OK(200, "비밀번호 변경 성공"),
    PWD_FIND_OK(200, "비밀번호 찾기 성공"),
    MEMBERS_SEARCH_OK(200, "유저 검색 성공"),
    RECORDS_INQUIRY_OK(200, "나의 기록 조회 성공"),
    MEMBER_CREATED(201, "회원 가입 성공"),
    MEMBER_DELETED(204, "회원 탈퇴 성공"),
    TODO_INQUIRY_OK(200, "투두 조회 성공"),
    TODO_MODIFY_OK(200, "투두 수정 성공"),
    TODO_CHECK_OK(200, "투두 완료 여부 조회 성공"),
    TODO_CRATED(201, "투두 생성 성공"),
    TODO_RE_CREATED(201, "투두 또하기 생성 성공"),
    TODO_DELETED(204, "투두 삭제 성공"),
    ITEMS_INQUIRY_OK(200, "보관함 조회 성공"),
    ITEM_USE_OK(200, "아이템 사용 성공"),
    STORE_INQUIRY_OK(200, "아이템 조회 성공"),
    ITEM_INQUIRY_OK(200, "아이템 상세 조회 성공"),
    ITEMS_BUY_OK(200, "아이템 구매 성공"),
    ITEMS_SELL_OK(200, "아이템 판매 성공"),
    CHARACTERS_INQUIRY_OK(200, "나의 캐릭터 조회 성공"),
    CHARACTERS_BUY_OK(200, "캐릭터 구매 성공"),
    CHARACTER_MODIFY_OK(200, "대표 캐릭터 변경 성공"),
    CHARACTER_SELL_OK(200, "캐릭터 판매 성공"),
    CHARACTER_INQUIRY_OK(200, "캐릭터 조회 성공"),
    CATEGORIES_INQUIRY_OK(200, "카테고리 전체 조회 성공"),
    CATEGORY_MODIFY_OK(200, "카테고리 수정 성공"),
    CATEGORY_ARRANGE_OK(200, "카테고리 순서 변경 성공"),
    CATEGORY_CREATED(201, "카테고리 생성 성공"),
    CATEGORY_DELETED(204, "카테고리 삭제 성공"),
    BETTINGS_INQUIRY_OK(200, "베팅 조회 성공"),
    BETTING_INQUIRY_OK(200, "베팅 단일 조회 성공"),
    BETTING_CREATED(201, "베팅 생성 성공"),
    BETTING_JOIN_CREATED(201, "베팅 참여 성공"),
    BETTING_DELETED(204, "베팅 삭제 성공"),
    FRIENDS_INQUIRY_OK(200, "친구 목록 성공"),
    FRIENDS_RANKING_OK(200, "랭킹 조회 성공"),
    FRIEND_BLOCK_OK(200, "친구 차단 성공"),
    FRIEND_BLOCK_LIST_OK(200, "친구 차단 목록 성공"),
    FRIEND_UNBLOCK_OK(204, "친구 차단 취소 성공"),
    FRIEND_CREATED(201, "친구 수락 성공"),
    FRIEND_REQUEST_CREATED(201, "친구 신청 성공"),
    FRIEND_REQUEST_CANCELED(204, "친구 신청 취소 성공"),
    FRIEND_DELETED(204, "친구 삭제 성공"),
    FRIEND_REQUEST_DELETED(204, "친구 거절 성공"),
    CHATROOMS_INQUIRY_OK(200, "전체 채팅방 조회 성공"),
    CHATROOM_CREATED(201, "채팅 참여 성공"),
    CHATS_INQUIRY_OK(200, "채팅 조회 성공"),
    CHAT_CREATED(201, "채팅 메시지 작성 성공")
    ;

    private final int httpStatusCode;
    private final String message;
}
