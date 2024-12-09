package site.doto.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordFindReq {
    @NotNull
    private String email;

    @NotNull
    private String authCode;

    @NotNull
    private String changePassword;

}
