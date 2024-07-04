package site.doto.domain.relation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.global.dto.SliceDto;

@Data
@NoArgsConstructor
public class RelationBlockListRes {
    private SliceDto<RelationDto> relations;

    public RelationBlockListRes(SliceDto<RelationDto> relationDtoSliceDto) {
        relations = relationDtoSliceDto;
    }
}
