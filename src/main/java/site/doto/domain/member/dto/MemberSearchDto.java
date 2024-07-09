package site.doto.domain.member.dto;

import lombok.Data;
import site.doto.domain.member.enums.MemberRelation;
import site.doto.domain.relation.enums.RelationStatus;

import static site.doto.domain.member.enums.MemberRelation.*;
import static site.doto.domain.relation.enums.RelationStatus.ACCEPTED;
import static site.doto.domain.relation.enums.RelationStatus.WAITING;

@Data
public class MemberSearchDto {
    private Long memberId;

    private String nickname;

    private String mainCharacterImg;

    private MemberRelation status;

    public MemberSearchDto(MemberDto memberDto) {
        this.memberId = memberDto.getMemberId();
        this.nickname = memberDto.getNickname();
        this.mainCharacterImg = memberDto.getMainCharacterImg();

        RelationStatus status = memberDto.getStatus();

        if(status == null) {
            this.status = NOT_FRIENDS;
        } else if(status.equals(ACCEPTED)) {
            this.status = FRIENDS;
        } else if(status.equals(WAITING)) {
            this.status = WAITING_FRIEND_REQUEST;
        }
    }
}
