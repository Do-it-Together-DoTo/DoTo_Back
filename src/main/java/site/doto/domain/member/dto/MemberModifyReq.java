package site.doto.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberModifyReq {
    @NotBlank
    private String nickname;

    private String description;

}
