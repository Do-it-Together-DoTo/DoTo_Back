package site.doto.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MembersSearchReq {
    @NotNull
    private String searchWord;

    private Long lastMemberId;
}
