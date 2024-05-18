package site.doto.domain.member.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.member.dto.*;
import site.doto.global.dto.ResponseDto;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @PostMapping("/signup")
    public ResponseDto<?> memberAdd(
            @RequestBody MemberAddReq memberAddReq) {
        return ResponseDto.success(MEMBER_CREATED, null);
    }

    @PostMapping("/email")
    public ResponseDto<?> authCodeSend(
            @RequestBody AuthCodeSendReq authCodeSendReq) {
        return ResponseDto.success(EMAIL_SEND_OK, null);
    }

    @PostMapping("/email/check")
    public ResponseDto<?> authCodeVerify(
            @RequestBody AuthCodeVerifyReq authCodeVerifyReq) {
        return ResponseDto.success(EMAIL_AUTH_OK, null);
    }

    @PostMapping("login")
    public ResponseDto<MemberDetailRes> memberDetail(
            @RequestBody MemberDetailsReq memberDetailsReq) {
        MemberDetailRes result = new MemberDetailRes(jwtToken);
        return ResponseDto.success(LOGIN_OK, result);
    }

    @PatchMapping("/modify")
    public ResponseDto<MemberModifyRes> memberModify(
            @RequestBody MemberModifyReq memberModifyReq) {
        MemberModifyRes result = new MemberModifyRes("수정한 닉네임", "이건 수정안할거지롱 ㅋ");
        return ResponseDto.success(MEMBER_MODIFY_OK, result);
    }

    @PatchMapping("/password/reset")
    public ResponseDto<?> passwordModify(
            @RequestBody PasswordModifyReq passwordModifyReq) {
        return ResponseDto.success(PWD_MODIFY_OK, null);
    }

    @PatchMapping("/password/find")
    public ResponseDto<?> passwordFind(
            @RequestBody PasswordFindReq passwordFindReq) {
        return ResponseDto.success(PWD_FIND_OK, null);
    }

    @DeleteMapping()
    public ResponseDto<?> memberRemove() {
        return ResponseDto.success(MEMBER_DELETED, null);
    }

}
