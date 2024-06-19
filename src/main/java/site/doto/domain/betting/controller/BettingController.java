package site.doto.domain.betting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.betting.dto.*;
import site.doto.domain.betting.service.BettingService;
import site.doto.global.dto.ResponseDto;
import site.doto.global.status_code.SuccessCode;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/betting")
public class BettingController {
    private final BettingService bettingService;

    @PostMapping
    public ResponseDto<?> bettingAdd(
            @RequestBody @Valid BettingAddReq bettingAddReq) {

        Long memberId = 1L;

        bettingService.addBetting(memberId, bettingAddReq);

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
        Long memberId = 1L;

        MyBettingListRes result = bettingService.findMyBetting(memberId);

        return ResponseDto.success(SuccessCode.BETTINGS_INQUIRY_OK, result);
    }

    @GetMapping("/open")
    public ResponseDto<OpenBettingListRes> openBettingList() {
        Long memberId = 1L;

        OpenBettingListRes result = bettingService.findOpenBetting(memberId);

        return ResponseDto.success(SuccessCode.BETTINGS_INQUIRY_OK, result);
    }

    @GetMapping("/{bettingId}")
    public ResponseDto<BettingDetailsRes> bettingDetails(
            @PathVariable Long bettingId) {
        Long memberId = 1L;

        BettingDetailsRes result = bettingService.findBetting(memberId, bettingId);

        return ResponseDto.success(SuccessCode.BETTING_INQUIRY_OK, result);
    }

    @DeleteMapping("/{bettingId}")
    public ResponseDto<?> bettingRemove(
            @PathVariable Long bettingId) {
        Long memberId = 1L;

        bettingService.removeBetting(bettingId, memberId);

        return ResponseDto.success(SuccessCode.BETTING_DELETED, null);
    }
}
