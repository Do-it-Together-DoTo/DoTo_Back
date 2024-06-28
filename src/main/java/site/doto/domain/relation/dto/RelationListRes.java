package site.doto.domain.relation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import site.doto.global.dto.SliceDto;

@Data
@NoArgsConstructor
public class RelationListRes {
    private SliceDto<RelationDto> friends;

    public RelationListRes(SliceDto<RelationDto> friendDtoSliceDto) {
        friends = friendDtoSliceDto;
    }

    public RelationListRes(Slice<RelationDto> friendDtoSlice) {
        friends = new SliceDto<>(friendDtoSlice);
    }
}
