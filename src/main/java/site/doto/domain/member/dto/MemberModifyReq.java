package site.doto.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberModifyReq {
    @NotBlank
    private String nickname;

    @Size(max = 20)
    private String description;

}
