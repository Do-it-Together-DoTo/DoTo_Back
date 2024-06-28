package site.doto.domain.relation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.relation.dto.*;
import site.doto.domain.relation.entity.Relation;
import site.doto.domain.relation.entity.RelationPK;
import site.doto.domain.relation.enums.RelationStatus;
import site.doto.domain.relation.repository.RelationRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.dto.SliceDto;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.time.LocalDateTime;
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

        RelationPK memberAndFriendPK = new RelationPK(member.getId(), friend.getId());
        RelationPK friendAndMemberPK = new RelationPK(friend.getId(), member.getId());

        Optional<Relation> memberToFriend = relationRepository.findById(memberAndFriendPK);
        Optional<Relation> friendToMember = relationRepository.findById(friendAndMemberPK);

        if(isRelationRequestInRedis(member.getId(), friend.getId())) {
            throw new CustomException(FRIEND_REQUEST_COOLDOWN);
        }

        if(memberToFriend.isEmpty() && friendToMember.isEmpty()) {
            relationRepository.save(new Relation(memberAndFriendPK, member, friend, WAITING));
            updateRelationRequestToRedis(member.getId(), friend.getId());
            return;
        }

        if(memberToFriend.isPresent()) {
            RelationStatus relationStatus = memberToFriend.get().getStatus();

            if(relationStatus.equals(WAITING)) {
                throw new CustomException(FRIEND_ALREADY_REQUESTING);
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
                relationRepository.updateRelationStatus(friendAndMemberPK, ACCEPTED);
                relationRepository.save(new Relation(memberAndFriendPK, member, friend, ACCEPTED));
            }
        }
    }

    public void addRelationResponse(Long memberId, RelationResponseReq relationResponseReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(relationResponseReq.getFriendId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RelationPK memberAndFriendPK = new RelationPK(member.getId(), friend.getId());
        RelationPK friendAndMemberPK = new RelationPK(friend.getId(), member.getId());

        Optional<Relation> friendToMember = relationRepository.findById(friendAndMemberPK);

        if(friendToMember.isEmpty()) {
            throw new CustomException(FRIEND_REQUEST_MISSING);
        }

        Relation existingFriendToMember = friendToMember.get();

        if(existingFriendToMember.getStatus().equals(ACCEPTED)) {
            throw new CustomException(FRIEND_ALREADY_ADDED);
        }

        if(existingFriendToMember.getStatus().equals(BLOCKED)) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        relationRepository.updateRelationStatus(friendAndMemberPK, ACCEPTED);
        relationRepository.save(new Relation(memberAndFriendPK, member, friend, ACCEPTED));
    }

    public void declineRelation(Long memberId, RelationDeclinedReq relationDeclinedReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(relationDeclinedReq.getFriendId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RelationPK friendAndMemberPK = new RelationPK(friend.getId(), member.getId());

        Optional<Relation> friendToMember = relationRepository.findById(friendAndMemberPK);

        if(friendToMember.isEmpty()) {
            throw new CustomException(FRIEND_REQUEST_MISSING);
        }

        Relation existingFriendToMember = friendToMember.get();

        if(existingFriendToMember.getStatus().equals(ACCEPTED)) {
            throw new CustomException(FRIEND_ALREADY_ADDED);
        }

        if(existingFriendToMember.getStatus().equals(BLOCKED)) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }

        relationRepository.delete(existingFriendToMember);
    }

    public void cancelRelation(Long memberId, RelationCanceledReq relationCanceledReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(relationCanceledReq.getFriendId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RelationPK memberAndFriendPK = new RelationPK(member.getId(), friend.getId());

        Optional<Relation> memberToFriend = relationRepository.findById(memberAndFriendPK);

        if(memberToFriend.isEmpty()) {
            throw new CustomException(FRIEND_REQUEST_MISSING);
        }

        Relation existingMemberToFriend = memberToFriend.get();

        if(existingMemberToFriend.getStatus().equals(ACCEPTED)) {
            throw new CustomException(FRIEND_ALREADY_ADDED);
        }

        if(existingMemberToFriend.getStatus().equals(BLOCKED)) {
            throw new CustomException(BLOCKED_MEMBER);
        }

        relationRepository.delete(existingMemberToFriend);
    }

    public void removeRelation(Long memberId, Long friendId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RelationPK memberAndFriendPK = new RelationPK(member.getId(), friend.getId());
        RelationPK friendAndMemberPK = new RelationPK(friend.getId(), member.getId());

        Optional<Relation> memberToFriend = relationRepository.findById(memberAndFriendPK);
        Optional<Relation> friendToMember = relationRepository.findById(friendAndMemberPK);

        if(memberToFriend.isEmpty()) {
            throw new CustomException(BAD_REQUEST);
        }

        if(friendToMember.isEmpty()) {
            throw new CustomException(BAD_REQUEST);
        }

        Relation existingMemberToFriend = memberToFriend.get();
        Relation existingFriendToMember = friendToMember.get();

        relationRepository.delete(existingMemberToFriend);
        relationRepository.delete(existingFriendToMember);
    }

    @Transactional(readOnly = true)
    public RelationListRes findRelation(Long memberId, RelationListReq relationListReq, Pageable pageable) {
        if((relationListReq.getLastFriendId() == null && relationListReq.getLastFriendLastUpload() != null)
                || (relationListReq.getLastFriendId() != null && relationListReq.getLastFriendLastUpload() == null)) {
            throw new CustomException(BIND_EXCEPTION);
        }

        Slice<Member> members = memberRepository.findAllByMemberIdAndStatus(memberId, relationListReq, pageable);

        SliceDto<RelationDto> relationDtoSliceDto = new SliceDto<>(members.map(member ->
            RelationDto.builder()
                    .memberId(member.getId())
                    .nickname(member.getNickname())
                    .mainCharacterImg(member.getMainCharacterImg())
                    .build()
        ));

        return new RelationListRes(relationDtoSliceDto);
    }

    public void blockRelation(Long memberId, RelationBlockReq relationBlockReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(relationBlockReq.getFriendId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RelationPK memberAndFriendPK = new RelationPK(member.getId(), friend.getId());
        RelationPK friendAndMemberPK = new RelationPK(friend.getId(), member.getId());

        Optional<Relation> memberToFriend = relationRepository.findById(memberAndFriendPK);
        Optional<Relation> friendToMember = relationRepository.findById(friendAndMemberPK);

        if(friendToMember.isPresent()) {
            Relation existingFriendToMember = friendToMember.get();

            if(existingFriendToMember.getStatus().equals(BLOCKED)) {
                throw new CustomException(MEMBER_NOT_FOUND);
            }
        }

        if(memberToFriend.isPresent()) {
            Relation existingMemberToFriend = memberToFriend.get();

            if(existingMemberToFriend.getStatus().equals(BLOCKED)) {
                throw new CustomException(BLOCKED_MEMBER);
            }
        }

        if(friendToMember.isPresent()) {
            Relation existingFriendToMember = friendToMember.get();
            relationRepository.delete(existingFriendToMember);
        }

        if(memberToFriend.isPresent()) {
            relationRepository.updateRelationStatus(memberAndFriendPK, BLOCKED);
        } else {
            relationRepository.save(new Relation(memberAndFriendPK, member, friend, BLOCKED));
        }
    }

    public void unblockRelation(Long memberId, RelationUnblockReq relationUnblockReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Member friend = memberRepository.findById(relationUnblockReq.getFriendId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        RelationPK memberAndFriendPK = new RelationPK(member.getId(), friend.getId());
        RelationPK friendAndMemberPK = new RelationPK(friend.getId(), member.getId());

        Optional<Relation> friendToMember = relationRepository.findById(friendAndMemberPK);

        if(friendToMember.isPresent()) {
            Relation existingFriendToMember = friendToMember.get();

            if(existingFriendToMember.getStatus().equals(BLOCKED)) {
                throw new CustomException(MEMBER_NOT_FOUND);
            }
        }

        Relation existingMemberToFriend = relationRepository.findById(memberAndFriendPK)
                .orElseThrow(() -> new CustomException(BAD_REQUEST));

        if(!existingMemberToFriend.getStatus().equals(BLOCKED)) {
            throw new CustomException(BAD_REQUEST);
        }

        relationRepository.delete(existingMemberToFriend);
    }

    private void updateRelationRequestToRedis(Long memberId, Long friendId) {
        String key = "relationRequest:" + memberId + ":" + friendId;
        redisUtils.setDataWithExpiration(key, "WAITING", 300L);
    }

    private boolean isRelationRequestInRedis(Long memberId, Long friendId) {
        String key = "relationRequest:" + memberId + ":" + friendId;
        Object data = redisUtils.getData(key);

        return data != null;
    }
}
