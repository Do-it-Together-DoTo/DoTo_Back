package site.doto.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberModifyReq {
    @NotBlank
    private String nickname;

    @Size(max = 20)
    private String description;

}
