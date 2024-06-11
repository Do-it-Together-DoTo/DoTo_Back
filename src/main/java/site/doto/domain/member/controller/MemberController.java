package site.doto.domain.member.controller;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.member.dto.*;
import site.doto.domain.member.enums.MemberRelation;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseDto<LoginRes> login(
            @RequestBody LoginReq loginReq) {
        LoginRes result = new LoginRes(jwtToken);
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

    @GetMapping("/search")
    public ResponseDto<?> membersSearch(
            @ModelAttribute MembersSearchReq membersSearchReq) {
        List<MembersSearchDto> members = new ArrayList<>();

        for(int i = 1; i <= 5; i++) {
            members.add(MembersSearchDto.builder()
                    .memberId(10000L + i)
                    .nickname("닉네임" + i)
                    .mainCharacterImg("이미지" + i)
                    .status(MemberRelation.FRIENDS)
                    .build());
        }

        for(int i = 6; i <= 10; i++) {
            members.add(MembersSearchDto.builder()
                    .memberId(20000L + i)
                    .nickname("닉네임" + i)
                    .mainCharacterImg("이미지" + i)
                    .status(MemberRelation.NOT_FRIENDS)
                    .build());
        }

        for(int i = 11; i <= 15; i++) {
            members.add(MembersSearchDto.builder()
                    .memberId(30000L + i)
                    .nickname("닉네임" + i)
                    .mainCharacterImg("이미지" + i)
                    .status(MemberRelation.WAITING_FRIEND_REQUEST)
                    .build());
        }

        Slice<MembersSearchDto> membersSearchDtoSlice = new SliceImpl<>(members);
        MembersSearchRes result = new MembersSearchRes(membersSearchDtoSlice);

        return ResponseDto.success(MEMBERS_SEARCH_OK, result);
    }
}
