package site.doto.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthCodeVerifyReq {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String authCode;

}
