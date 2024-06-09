package site.doto.domain.member.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.member.enums.MemberRelation;

@Data
@Builder
public class MembersSearchDto {
    private Long memberId;

    private String nickname;

    private String mainCharacterImg;

    private MemberRelation status;
}
