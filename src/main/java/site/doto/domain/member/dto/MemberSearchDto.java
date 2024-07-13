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

    public MemberSearchDto(Long memberId, String nickname, String mainCharacterImg, RelationStatus status) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.mainCharacterImg = mainCharacterImg;

        if(status == null) {
            this.status = NOT_FRIENDS;
        } else if(status.equals(ACCEPTED)) {
            this.status = FRIENDS;
        } else if(status.equals(WAITING)) {
            this.status = WAITING_FRIEND_REQUEST;
        }
    }
}
