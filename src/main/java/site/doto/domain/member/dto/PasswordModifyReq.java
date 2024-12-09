package site.doto.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordModifyReq {
    @NotNull
    private String currentPassword;

    @NotNull
    private String changePassword;

}
