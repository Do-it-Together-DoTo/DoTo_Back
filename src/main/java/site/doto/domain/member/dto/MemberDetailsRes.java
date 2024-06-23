package site.doto.domain.member.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.member.entity.Member;

@Data
@Builder
public class MemberDetailsRes {
    private String nickname;

    private String description;

    private Integer mainCharacterExp;

    private Integer mainCharacterLevel;

    private String mainCharacterImg;

    private Integer coin;

    public static MemberDetailsRes toDto(Member member) {
        Integer exp = member.getMainCharacter().getExp();

        return MemberDetailsRes.builder()
                .nickname(member.getNickname())
                .description(member.getDescription())
                .mainCharacterExp(calculateExp(exp))
                .mainCharacterLevel(calculateLevel(exp))
                .mainCharacterImg(member.getMainCharacterImg())
                .coin(member.getCoin())
                .build();
    }

    private static Integer calculateLevel(Integer exp) {
        return exp/100;
    }

    private static Integer calculateExp(Integer exp) {
        return exp%100;
    }
}
