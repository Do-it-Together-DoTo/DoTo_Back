package site.doto.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthCodeSendReq {
    @NotNull
    private String email;

}
