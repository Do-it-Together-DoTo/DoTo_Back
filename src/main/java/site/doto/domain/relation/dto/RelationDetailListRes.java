package site.doto.domain.relation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.global.dto.SliceDto;

@Data
@NoArgsConstructor
public class RelationDetailListRes {
    private SliceDto<RelationDetailDto> relations;

    public RelationDetailListRes(SliceDto<RelationDetailDto> relationDetailDtoSliceDto) {
        relations = relationDetailDtoSliceDto;
    }
}
