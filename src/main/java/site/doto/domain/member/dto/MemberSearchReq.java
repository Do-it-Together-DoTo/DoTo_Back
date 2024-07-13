package site.doto.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberSearchReq {
    @NotBlank
    private String searchWord;

    private Long lastMemberId;
}
