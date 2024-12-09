package site.doto.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberSearchReq {
    @NotBlank
    private String searchWord;

    private Long lastMemberId;
}
