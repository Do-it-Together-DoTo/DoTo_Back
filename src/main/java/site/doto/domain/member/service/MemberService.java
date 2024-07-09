package site.doto.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.member.dto.MemberDto;
import site.doto.domain.member.dto.MemberSearchDto;
import site.doto.domain.member.dto.MemberSearchReq;
import site.doto.domain.member.dto.MemberSearchRes;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.dto.SliceDto;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberSearchRes findMembers(Long memberId, MemberSearchReq memberSearchReq, Pageable pageable) {
        String searchWord = memberSearchReq.getSearchWord();
        Long lastMemberId = memberSearchReq.getLastMemberId();

        Slice<MemberDto> members = memberRepository.findAllBySearchWord(memberId, searchWord, lastMemberId, pageable);

        SliceDto<MemberSearchDto> memberSearchDtoSliceDto = new SliceDto<>(members.map(MemberSearchDto::new));

        return new MemberSearchRes(memberSearchDtoSliceDto);
    }
}
