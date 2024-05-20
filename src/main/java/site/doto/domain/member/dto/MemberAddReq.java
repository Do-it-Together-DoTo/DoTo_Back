package site.doto.domain.member.dto;

import lombok.Data;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.type.MemberType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class MemberAddReq {
    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private String authCode;

    Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .description("")
                .coin(0)
                .type(MemberType.LOCAL)
                .lastUpload(LocalDateTime.now())
                .build();
    }

}