package site.doto.domain.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.global.dto.SliceDto;

@Data
@NoArgsConstructor
public class MemberSearchRes {
    SliceDto<MemberSearchDto> searchResult;

    public MemberSearchRes(SliceDto<MemberSearchDto> membersSearchDtoSliceDto) {
        searchResult = membersSearchDtoSliceDto;
    }
}
