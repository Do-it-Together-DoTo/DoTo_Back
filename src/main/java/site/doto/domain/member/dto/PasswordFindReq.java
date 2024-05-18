package site.doto.domain.member.dto;

import lombok.Data;

@Data
public class PasswordFindReq {
    private String email;

    private String authCode;

    private String changePassword;

}
