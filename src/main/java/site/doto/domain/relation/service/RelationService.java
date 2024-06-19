package site.doto.domain.relation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.relation.dto.RelationRequestReq;
import site.doto.domain.relation.dto.RelationResponseReq;
import site.doto.domain.relation.entity.Relation;
import site.doto.domain.relation.entity.RelationPK;
import site.doto.domain.relation.enums.RelationStatus;
import site.doto.domain.relation.repository.RelationRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.util.Optional;

import static site.doto.domain.relation.enums.RelationStatus.*;
import static site.doto.global.status_code.ErrorCode.*;
import static site.doto.global.status_code.ErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class RelationService {
    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;
    private final RedisUtils redisUtils;

    public void addRelationRequest(Long memberId, RelationRequestReq relationRequestReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(relationRequestReq.getFriendId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        if(member.getId().equals(friend.getId())) {
            throw new CustomException(FRIEND_SELF_REQUEST);
        }

        Optional<Relation> memberToFriend = relationRepository.findById(new RelationPK(member.getId(), friend.getId()));
        Optional<Relation> friendToMember = relationRepository.findById(new RelationPK(friend.getId(), member.getId()));

        if(memberToFriend.isEmpty() && friendToMember.isEmpty()) {
            relationRepository.save(new Relation(new RelationPK(member.getId(), friend.getId()), member, friend, WAITING));
            updateRelationRequestToRedis(member.getId(), friend.getId());
            return;
        }

        if(memberToFriend.isPresent()) {
            RelationStatus relationStatus = memberToFriend.get().getStatus();

            if(relationStatus.equals(WAITING)) {
                throw new CustomException(FRIEND_ALREADY_REQUESTING);
            }

            if(isRelationRequestExistsInRedis(member.getId(), friend.getId())) {
                throw new CustomException(FRIEND_REQUEST_COOLDOWN);
            }

            if(relationStatus.equals(BLOCKED)) {
                throw new CustomException(BLOCKED_MEMBER);
            }

            if(relationStatus.equals(ACCEPTED)) {
                throw new CustomException(FRIEND_ALREADY_ADDED);
            }
        }

        if(friendToMember.isPresent()) {
            RelationStatus relationStatus = friendToMember.get().getStatus();

            if(relationStatus.equals(BLOCKED)) {
                throw new CustomException(MEMBER_NOT_FOUND);
            }

            if(relationStatus.equals(WAITING)) {
                relationRepository.updateRelationStatus(new RelationPK(friend.getId(), member.getId()), ACCEPTED);
                relationRepository.save(new Relation(new RelationPK(member.getId(), friend.getId()),member, friend, ACCEPTED));
            }
        }
    }

    public void addRelationResponse(Long memberId, RelationResponseReq relationResponseReq) {
        Member friend = memberRepository.findById(relationResponseReq.getFriendId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Optional<Relation> friendToMember = relationRepository.findById(new RelationPK(friend.getId(), member.getId()));
        Optional<Relation> memberToFriend = relationRepository.findById(new RelationPK(member.getId(), friend.getId()));

        if(friendToMember.isEmpty()) {
            throw new CustomException(FRIEND_REQUEST_MISSING);
        }

        Relation existedToRelation = friendToMember.get();

        if(existedToRelation.getStatus().equals(ACCEPTED)) {
            throw new CustomException(FRIEND_ALREADY_ADDED);
        }

        if(existedToRelation.getStatus().equals(BLOCKED)) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        if(memberToFriend.isPresent()) {
            Relation existingFromRelation = memberToFriend.get();

            if(existingFromRelation.getStatus().equals(BLOCKED)) {
                throw new CustomException(BLOCKED_MEMBER);
            }
        }

        relationRepository.updateRelationStatus(new RelationPK(friend.getId(), member.getId()), ACCEPTED);
        relationRepository.save(new Relation(new RelationPK(member.getId(), friend.getId()), member, friend, ACCEPTED));
    }

    private void updateRelationRequestToRedis(Long memberId, Long friendId) {
        String key = "relationRequest:" + memberId + ":" + friendId;
        redisUtils.setDataWithExpiration(key, "WAITING", 300L);
    }

    private boolean isRelationRequestExistsInRedis(Long memberId, Long friendId) {
        String key = "relationRequest:" + memberId + ":" + friendId;
        Object data = redisUtils.getData(key);

        return data != null;
    }
}
