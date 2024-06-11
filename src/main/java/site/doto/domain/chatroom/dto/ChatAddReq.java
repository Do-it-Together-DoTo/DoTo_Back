package site.doto.domain.chatroom.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChatAddReq {
    @NotNull
    String contents;
}
