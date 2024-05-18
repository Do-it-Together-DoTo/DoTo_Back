package site.doto.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberModifyRes {
    private String nickname;

    private String description;

}
