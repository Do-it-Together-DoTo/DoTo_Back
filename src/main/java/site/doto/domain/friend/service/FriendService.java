package site.doto.domain.friend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.friend.dto.FriendRequestReq;
import site.doto.domain.friend.entity.Friend;
import site.doto.domain.friend.entity.FriendPK;
import site.doto.domain.friend.enums.FriendRelation;
import site.doto.domain.friend.repository.FriendRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.util.Optional;

import static site.doto.domain.friend.enums.FriendRelation.*;
import static site.doto.global.status_code.ErrorCode.*;
import static site.doto.global.status_code.ErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final RedisUtils redisUtils;

    public void addFriendRequest(Long toMemberId, FriendRequestReq friendRequestReq) {
        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member fromMember = memberRepository.findById(friendRequestReq.getFromMemberId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        if(toMember.getId().compareTo(fromMember.getId()) == 0) {
            throw new CustomException(FRIEND_SELF_REQUEST);
        }

        Optional<Friend> toFriend = friendRepository.findById(new FriendPK(toMember.getId(), fromMember.getId()));
        Optional<Friend> fromFriend = friendRepository.findById(new FriendPK(fromMember.getId(), toMember.getId()));

        if(toFriend.isEmpty() && fromFriend.isEmpty()) {
            redisUtils.updateFriendRequestToRedis(toMember.getId(), fromMember.getId());
            friendRepository.save(new Friend(new FriendPK(toMember.getId(), fromMember.getId()), toMember, fromMember, WAITING));
            return;
        }

        if(toFriend.isPresent()) {
            FriendRelation friendRelation = toFriend.get().getStatus();

            if(friendRelation.equals(WAITING)) {
                throw new CustomException(FRIEND_ALREADY_REQUESTING);
            }

            if(isFriendRequestExistsInRedis(toMember.getId(), fromMember.getId())) {
                throw new CustomException(FRIEND_REQUEST_COOLDOWN);
            }

            if(friendRelation.equals(BLOCKED)) {
                throw new CustomException(BLOCKED_MEMBER);
            }

            if(friendRelation.equals(ACCEPTED)) {
                throw new CustomException(FRIEND_ALREADY_ADDED);
            }
        }

        if(fromFriend.isPresent()) {
            FriendRelation friendRelation = fromFriend.get().getStatus();

            if(friendRelation.equals(BLOCKED)) {
                throw new CustomException(MEMBER_NOT_FOUND);
            }

            if(friendRelation.equals(WAITING)) {
                friendRepository.updateFriendRelation(fromMember.getId(), toMember.getId(), ACCEPTED);
                friendRepository.save(new Friend(new FriendPK(toMember.getId(), fromMember.getId()),toMember, fromMember, ACCEPTED));
            }
        }
    }

    private boolean isFriendRequestExistsInRedis(Long toMemberId, Long fromMemberId) {
        String key = "friendRequest:" + toMemberId + ":" + fromMemberId;
        Object data = redisUtils.getData(key);
        return data != null;
    }
}
