package site.doto.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthCodeVerifyReq {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String authCode;

}
