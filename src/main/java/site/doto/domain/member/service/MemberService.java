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

import static site.doto.global.status_code.ErrorCode.MEMBER_NOT_FOUND;

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
}

