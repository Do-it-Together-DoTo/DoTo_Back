package site.doto.domain.chat.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChatAddReq {
    @NotNull
    private String contents;
}
