package site.doto.global.status_code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    BAD_REQUEST(400, "잘못된 요청입니다."),
    BIND_EXCEPTION(400, "데이터 형식이 잘못됐습니다."),
    UNAUTHORIZED(401, "로그인 정보가 없습니다."),
    FORBIDDEN(403, "접근 권한이 없습니다."),
    NOT_FOUND(404, "요청한 URL이 존재하지 않습니다."),
    CRITERIA_MISMATCH(400, "랭킹 기준이 올바르지 않습니다."),
    PASSWORD_MISMATCH(400, "비밀번호가 서로 일치하지 않습니다."),
    WITHDRAWAL_NOT_ALLOWED(400, "베팅 진행중에는 탈퇴할 수 없습니다."),
    AUTHCODE_MISMATCH(401, "이메일 인증번호가 일치하지 않습니다."),
    LOGIN_FAILED(401, "로그인에 실패했습니다."),
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    MAIL_SEND_FAILED(500, "메일 전송에 실패했습니다."),
    NICKNAME_DUPLICATED(409, "이미 사용중인 닉네임입니다."),
    EMAIL_DUPLICATED(409, "이미 사용중인 이메일입니다."),
    ITEMS_NOT_ENOUGH(400, "아이템 개수가 부족합니다."),
    COIN_NOT_ENOUGH(400, "코인이 부족합니다."),
    ITEM_NOT_FOUND(404, "존재하지 않는 아이템입니다."),
    TODO_LIMIT(400, "더이상 투두를 생성할 수 없습니다."),
    CATEGORY_INACTIVATED(400, "비활성화된 카테고리에 투두를 생성할 수 없습니다."),
    TODO_NOT_FOUND(404, "존재하지 않는 투두입니다."),
    FRIEND_ALREADY_REQUESTING(400, "친구 신청이 이미 진행중입니다."),
    FRIEND_SELF_REQUEST(400, "자기 자신에겐 친구 신청을 할 수 없습니다"),
    NOT_FRIEND(403, "친구가 아닌 상태에서 접근할 수 없습니다."),
    FRIEND_REQUEST_MISSING(404, "유효하지 않은 요청입니다."),
    CHATROOM_NOT_FOUND(404, "존재하지 않는 채팅방입니다."),
    BETTING_NOT_PARTICIPATION(403, "채팅방에 참여할 수 없습니다."),
    COLOR_NOT_FOUND(400, "존재하지 않는 색상입니다."),
    ACTIVATED_CATEGORY_LIMIT(400, "더 이상 카테고리를 생성할 수 없습니다."),
    CATEGORY_NOT_FOUND(404, "존재하지 않는 카테고리입니다."),
    NOT_MATCH_CATEGORIES(400, "카테고리 개수가 맞지 않습니다"),
    DELETE_NOT_ALLOWED(400, "베팅이 진행중이므로 삭제할 수 없습니다."),
    BETTING_ALREADY_HOLDING(400, "이미 개최한 베팅이 존재합니다."),
    BETTING_ALREADY_JOINING(400, "이미 참여한 베팅입니다."),
    BETTING_CANCEL_FAILED(400, "다른 사람이 참여한 베팅은 취소할 수 없습니다."),
    BETTING_SELF_JOIN(400, "자신이 연 베팅에는 참여할 수 없습니다."),
    BETTING_CLOSED(400, "종료된 베팅입니다.."),
    TODO_ALREADY_PAST(400, "과거의 투두는 베팅을 열 수 없습니다."),
    TODO_ALREADY_DONE(400, "이미 완료한 투두는 베팅을 열 수 없습니다."),
    BETTING_NOT_FOUND(404, "존재하지 않는 베팅입니다."),
    MAIN_CHARACTER_DELETE(400, "대표 캐릭터는 삭제할 수 없습니다."),
    CHARACTER_NOT_FOUND(404, "존재하지 않는 캐릭터입니다."),
    FRIEND_ALREADY_ADDED(400, "이미 추가된 친구입니다."),
    BLOCKED_MEMBER(400, "차단 중인 이용자입니다."),
    FRIEND_REQUEST_COOLDOWN(400, "친구 신청은 5분에 한 번만 가능합니다.")
    ;

    private final int httpStatusCode;
    private final String message;
}
