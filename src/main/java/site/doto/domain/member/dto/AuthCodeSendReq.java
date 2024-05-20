package site.doto.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthCodeSendReq {
    @NotNull
    private String email;

}
