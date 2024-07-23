package site.doto.domain.member.controller;

import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.member.dto.*;
import site.doto.domain.member.enums.RankingCriteria;
import site.doto.domain.member.service.MemberService;
import site.doto.global.dto.ResponseDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    private final MemberService memberService;

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
        LoginRes result = new LoginRes(jwtToken, 1L);
        return ResponseDto.success(LOGIN_OK, result);
    }

    @PatchMapping("/modify")
    public ResponseDto<MemberModifyRes> memberModify(
            @RequestBody @Valid MemberModifyReq memberModifyReq) {
        Long memberId = 1L;

        MemberModifyRes result = memberService.modifyMember(memberId, memberModifyReq);

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
    public ResponseDto<MemberSearchRes> membersSearch(
            @ModelAttribute @Valid MemberSearchReq memberSearchReq,
            @PageableDefault(size = 20) Pageable pageable) {
        Long memberId = 1L;

        MemberSearchRes result = memberService.findMembers(memberId, memberSearchReq, pageable);

        return ResponseDto.success(MEMBERS_SEARCH_OK, result);
    }

    @GetMapping("/ranking")
    public ResponseDto<?> memberRankList(
            @Param("order") RankingCriteria order) {
        List<RankDto> ranks = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ranks.add(RankDto.builder()
                    .memberId((long) i)
                    .memberNickname("닉네임" + i)
                    .mainCharacterImg("이미지 주소" + i)
                    .score(1100 - i * 100)
                    .rank(i)
                    .build());
        }

        MemberRankRes result = new MemberRankRes();
        result.setRanks(ranks);

        return ResponseDto.success(FRIENDS_RANKING_OK, result);
    }

    @GetMapping
    public ResponseDto<MemberDetailsRes> memberDetails() {
        Long memberId = 1L;

        MemberDetailsRes result = memberService.findMember(memberId);

        return ResponseDto.success(MEMBER_INQUIRY_OK, result);
    }
}
