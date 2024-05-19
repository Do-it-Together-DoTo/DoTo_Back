package site.doto.domain.member.dto;

import lombok.Data;
경
import javax.validation.constraints.NotNull;

@Data
public class PasswordModifyReq {
    @NotNull
    private String currentPassword;

    @NotNull
    private String changePassword;

}
