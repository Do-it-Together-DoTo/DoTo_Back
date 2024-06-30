package site.doto.domain.relation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.relation.dto.*;
import site.doto.domain.relation.service.RelationService;
import site.doto.global.dto.ResponseDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class RelationController {
    private final RelationService relationService;

    @PostMapping("/friends/request")
    public ResponseDto<?> relationRequest(
            @RequestBody @Valid RelationRequestReq relationRequestReq) {
        Long memberId = 1L;

        relationService.addRelationRequest(memberId, relationRequestReq);

        return ResponseDto.success(FRIEND_REQUEST_CREATED, null);
    }

    @PostMapping("/friends/response")
    public ResponseDto<?> relationResponse(
            @RequestBody @Valid RelationResponseReq relationResponseReq) {
        Long memberId = 1L;

        relationService.addRelationResponse(memberId, relationResponseReq);

        return ResponseDto.success(FRIEND_CREATED, null);
    }

    @DeleteMapping("/friends/response")
    public ResponseDto<?> relationDeclined(
            @RequestBody @Valid RelationDeclinedReq relationDeclinedReq) {
        Long memberId = 1L;

        relationService.declineRelation(memberId, relationDeclinedReq);

        return ResponseDto.success(FRIEND_REQUEST_DELETED, null);
    }

    @DeleteMapping("/friends/request")
    public ResponseDto<?> relationCanceled(
            @RequestBody @Valid RelationCanceledReq relationCanceledReq) {
        Long memberId = 1L;

        relationService.cancelRelation(memberId, relationCanceledReq);

        return ResponseDto.success(FRIEND_REQUEST_CANCELED, null);
    }

    @DeleteMapping("/friends/{friendId}")
    public ResponseDto<?> relationRemove(
            @PathVariable Long friendId) {
        Long memberId = 1L;

        relationService.removeRelation(memberId, friendId);

        return ResponseDto.success(FRIEND_DELETED, null);
    }

    @GetMapping("/members/friends")
    public ResponseDto<RelationDetailListRes> relationDetailList(
            @ModelAttribute RelationDetailListReq relationDetailListReq,
            @PageableDefault(size = 20) Pageable pageable) {
        Long memberId = 1L;

        RelationDetailListRes result = relationService.findRelationDetail(memberId, relationDetailListReq, pageable);

        return ResponseDto.success(FRIENDS_INQUIRY_DETAIL_OK, result);
    }

    @GetMapping("/friends")
    public ResponseDto<RelationListRes> relationList(
            @ModelAttribute RelationListReq relationListReq,
            @PageableDefault(size = 20) Pageable pageable) {
        Long memberId = 1L;

        RelationListRes result = relationService.findRelation(memberId, relationListReq, pageable);

        return ResponseDto.success(FRIENDS_INQUIRY_OK, result);
    }

    @GetMapping("/friends/block")
    ResponseDto<?> relationBlockList(
            @ModelAttribute RelationBlockListReq relationBlockListReq) {
        List<RelationDto> friends = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            friends.add(RelationDto.builder()
                    .memberId(10000L + i)
                    .nickname("닉네임" + i)
                    .mainCharacterImg("메인 캐릭터" + i)
                    .build());
        }

        Slice<RelationDto> friendDtoSlice = new SliceImpl<>(friends);
        RelationListRes result = new RelationListRes(friendDtoSlice);

        return ResponseDto.success(FRIEND_BLOCK_LIST_OK, result);
    }

    @PostMapping("/friends/block")
    ResponseDto<?> relationBlock(
            @RequestBody @Valid RelationBlockReq relationBlockReq) {
        Long memberId = 1L;

        relationService.blockRelation(memberId, relationBlockReq);

        return ResponseDto.success(FRIEND_BLOCK_OK, null);
    }

    @DeleteMapping("/friends/block")
    ResponseDto<?> relationUnblock(
            @RequestBody @Valid RelationUnblockReq relationUnblockReq) {
        Long memberId = 1L;

        relationService.unblockRelation(memberId, relationUnblockReq);

        return ResponseDto.success(FRIEND_UNBLOCK_OK, null);
    }
}
