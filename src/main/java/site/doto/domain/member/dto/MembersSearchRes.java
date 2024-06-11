package site.doto.domain.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import site.doto.global.dto.SliceDto;

@Data
@NoArgsConstructor
public class MembersSearchRes {
    SliceDto<MembersSearchDto> searchResult;

    public MembersSearchRes(Slice<MembersSearchDto> membersSearchDtoSlice) {
        searchResult = new SliceDto<>(membersSearchDtoSlice);
    }
}
