package site.doto.domain.member.dto;

import lombok.Data;

@Data
public class PasswordModifyReq {
    private String currentPassword;

    private String changePassword;

}
