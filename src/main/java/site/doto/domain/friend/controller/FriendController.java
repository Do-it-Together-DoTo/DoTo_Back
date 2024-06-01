package site.doto.domain.friend.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.friend.dto.*;
import site.doto.domain.friend.enums.FriendRelation;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/members/friends")
public class FriendController {
    @PostMapping("/request")
    public ResponseDto<?> friendRequest(
            @RequestBody FriendRequestReq friendRequestReq) {

        return ResponseDto.success(FRIEND_REQUEST_CREATED, null);
    }

    @PostMapping("/response")
    public ResponseDto<?> friendResponse(
            @RequestBody FriendResponseReq friendResponseReq) {

        return ResponseDto.success(FRIEND_CREATED, null);
    }

    @DeleteMapping("/response")
    public ResponseDto<?> friendDeclined(
            @RequestBody FriendDeclinedReq friendDeclinedReq) {

        return ResponseDto.success(FRIEND_REQUEST_DELETED, null);
    }

    @DeleteMapping("/request")
    public ResponseDto<?> friendCanceled(
            @RequestBody FriendCanceledReq friendCanceledReq) {

        return ResponseDto.success(FRIEND_REQUEST_CANCELED, null);
    }

    @DeleteMapping("/{memberId}")
    public ResponseDto<?> friendRemove(
            @PathVariable Long memberId) {

        return ResponseDto.success(FRIEND_DELETED, null);
    }

    @GetMapping
    public ResponseDto<FriendListRes> friendList(
            @RequestBody FriendListReq friendListReq) {
        List<FriendDto> friends = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            friends.add(FriendDto.builder()
                    .memberId(10000L + i)
                    .mainCharacterId(10000L + i)
                    .build());
        }

        FriendListRes result = new FriendListRes();
        result.setFriends(friends);

        return ResponseDto.success(FRIENDS_INQUIRY_OK, result);
    }

    @PostMapping("/block")
    ResponseDto<?> friendBlock(
            @RequestBody FriendBlockReq friendBlockReq) {

        return ResponseDto.success(FRIEND_BLOCK_OK, null);
    }

    @DeleteMapping("/block")
    ResponseDto<?> friendUnblock(
            @RequestBody FriendUnblockReq friendUnblockReq) {

        return ResponseDto.success(FRIEND_UNBLOCK_OK, null);
    }
}
