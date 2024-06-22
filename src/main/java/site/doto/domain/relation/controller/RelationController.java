package site.doto.domain.relation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.relation.dto.*;
import site.doto.domain.relation.service.RelationService;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class RelationController {
    private final RelationService relationService;

    @PostMapping("/request")
    public ResponseDto<?> relationRequest(
            @RequestBody RelationRequestReq relationRequestReq) {
        Long memberId = 1L;

        relationService.addRelationRequest(memberId, relationRequestReq);

        return ResponseDto.success(FRIEND_REQUEST_CREATED, null);
    }

    @PostMapping("/response")
    public ResponseDto<?> relationResponse(
            @RequestBody RelationResponseReq relationResponseReq) {
        Long memberId = 1L;

        relationService.addRelationResponse(memberId, relationResponseReq);

        return ResponseDto.success(FRIEND_CREATED, null);
    }

    @DeleteMapping("/response")
    public ResponseDto<?> relationDeclined(
            @RequestBody RelationDeclinedReq relationDeclinedReq) {
        Long memberId = 1L;

        relationService.declineRelation(memberId, relationDeclinedReq);

        return ResponseDto.success(FRIEND_REQUEST_DELETED, null);
    }

    @DeleteMapping("/request")
    public ResponseDto<?> relationCanceled(
            @RequestBody RelationCanceledReq relationCanceledReq) {

        return ResponseDto.success(FRIEND_REQUEST_CANCELED, null);
    }

    @DeleteMapping("/{memberId}")
    public ResponseDto<?> relationRemove(
            @PathVariable Long memberId) {

        return ResponseDto.success(FRIEND_DELETED, null);
    }

    @GetMapping
    public ResponseDto<RelationListRes> relationList(
            @ModelAttribute RelationListReq relationListReq) {
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

        return ResponseDto.success(FRIENDS_INQUIRY_OK, result);
    }

    @GetMapping("/block")
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

    @PostMapping("/block")
    ResponseDto<?> relationBlock(
            @RequestBody RelationBlockReq relationBlockReq) {

        return ResponseDto.success(FRIEND_BLOCK_OK, null);
    }

    @DeleteMapping("/block")
    ResponseDto<?> relationUnblock(
            @RequestBody RelationUnblockReq relationUnblockReq) {

        return ResponseDto.success(FRIEND_UNBLOCK_OK, null);
    }
}
