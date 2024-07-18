package site.doto.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.member.dto.*;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.dto.SliceDto;
import site.doto.global.exception.CustomException;

import java.util.Optional;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberDetailsRes findMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        return MemberDetailsRes.toDto(member);
    }

    @Transactional(readOnly = true)
    public MemberSearchRes findMembers(Long memberId, MemberSearchReq memberSearchReq, Pageable pageable) {
        String searchWord = memberSearchReq.getSearchWord().replace(" ", "");
        Long lastMemberId = memberSearchReq.getLastMemberId();

        Slice<MemberSearchDto> members = memberRepository.findAllBySearchWord(memberId, searchWord, lastMemberId, pageable);

        SliceDto<MemberSearchDto> memberSearchDtoSliceDto = new SliceDto<>(members);

        return new MemberSearchRes(memberSearchDtoSliceDto);
    }

    public MemberModifyRes modifyMember(Long memberId, MemberModifyReq memberModifyReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        updateNickname(member, memberModifyReq.getNickname().replace(" ", ""));
        updateDescription(member, memberModifyReq.getDescription());

        memberRepository.save(member);

        return MemberModifyRes.toDto(member);
    }

    private void updateNickname(Member member, String nickname) {
        Optional<Member> existingMember = memberRepository.findByNickname(nickname);

        if(existingMember.isPresent()) {
            throw new CustomException(NICKNAME_DUPLICATED);
        }

        member.updateNickname(nickname);
    }

    private void updateDescription(Member member, String description) {
        if(description.length() > 20) {
            throw new CustomException(BAD_REQUEST);
        }

        member.updateDescription(description);
    }
}

