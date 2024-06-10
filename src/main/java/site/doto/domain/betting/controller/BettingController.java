package site.doto.domain.betting.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.betting.dto.*;
import site.doto.global.dto.ResponseDto;
import site.doto.global.status_code.SuccessCode;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/betting")
public class BettingController {
    @PostMapping
    public ResponseDto<?> bettingAdd(
            @RequestBody BettingAddReq bettingAddReq) {
        return ResponseDto.success(SuccessCode.BETTING_CREATED, null);
    }

    @PostMapping("/{bettingId}")
    public ResponseDto<?> bettingJoin(
            @PathVariable Long bettingId,
            @RequestBody BettingJoinReq bettingJoinReq) {
        return ResponseDto.success(SuccessCode.BETTING_JOIN_CREATED, null);
    }

    @GetMapping
    public ResponseDto<MyBettingListRes> myBettingList() {
        MyBettingListRes result = new MyBettingListRes();

        BettingDetailsRes myBetting = BettingDetailsRes.builder()
                .memberId(1L)
                .memberNickname("닉네임")
                .mainCharacterImg("이미지 주소")
                .bettingId(10001L)
                .bettingName("베팅 이름")
                .build();

        List<BettingDetailsRes> joiningBetting = new ArrayList<>();
        List<BettingDetailsRes> closedBetting = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            joiningBetting.add(BettingDetailsRes.builder()
                    .memberId(1L + i)
                    .memberNickname("친구" + i)
                    .mainCharacterImg("이미지 주소" + i)
                    .bettingId(10001L + i)
                    .bettingName("베팅 이름" + i)
                    .build());

            closedBetting.add(BettingDetailsRes.builder()
                    .memberId(1L + i)
                    .memberNickname("친구" + i)
                    .mainCharacterImg("이미지 주소" + i)
                    .bettingId(10011L + i)
                    .bettingName("베팅 이름" + (10 + i))
                    .build());
        }

        result.setMyBetting(myBetting);
        result.setJoiningBetting(joiningBetting);
        result.setClosedBetting(closedBetting);

        return ResponseDto.success(SuccessCode.BETTINGS_INQUIRY_OK, result);
    }

    @GetMapping("/open")
    public ResponseDto<OpenBettingListRes> openBettingList() {
        OpenBettingListRes result = new OpenBettingListRes();

        List<BettingDetailsRes> openBetting = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            openBetting.add(BettingDetailsRes.builder()
                    .memberId(1L + i)
                    .memberNickname("친구" + i)
                    .mainCharacterImg("이미지 주소" + i)
                    .bettingId(10021L + i)
                    .bettingName("베팅 이름" + (20 + i))
                    .build());
        }

        result.setOpenBetting(openBetting);

        return ResponseDto.success(SuccessCode.BETTINGS_INQUIRY_OK, result);
    }

    @GetMapping("/{bettingId}")
    public ResponseDto<BettingDetailsRes> bettingDetails(
            @PathVariable Long bettingId) {

        BettingDetailsRes result = BettingDetailsRes.builder()
                .memberId(1L)
                .memberNickname("닉네임")
                .mainCharacterImg("이미지 주소")
                .bettingId(10001L)
                .bettingName("베팅 이름")
                .build();

        return ResponseDto.success(SuccessCode.BETTING_INQUIRY_OK, result);
    }

    @DeleteMapping("/{bettingId}")
    public ResponseDto<?> bettingRemove(
            @PathVariable Long bettingId) {
        return ResponseDto.success(SuccessCode.BETTING_DELETED, null);
    }
}
