package site.doto.domain.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatAddReq {
    @NotNull
    private String contents;
}
