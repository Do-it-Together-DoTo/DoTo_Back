package site.doto.domain.member.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.member.entity.Member;

@Data
@Builder
public class MemberModifyRes {
    private String nickname;

    private String description;

    public static MemberModifyRes toDto(Member member) {
        return MemberModifyRes.builder()
                .nickname(member.getNickname())
                .description(member.getDescription())
                .build();
    }
}
