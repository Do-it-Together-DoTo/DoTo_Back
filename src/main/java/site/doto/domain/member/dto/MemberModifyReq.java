package site.doto.domain.member.dto;

import lombok.Data;

@Data
public class MemberModifyReq {
    private String nickname;

    private String description;

}
