package site.doto.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PasswordFindReq {
    @NotNull
    private String email;

    @NotNull
    private String authCode;

    @NotNull
    private String changePassword;

}
