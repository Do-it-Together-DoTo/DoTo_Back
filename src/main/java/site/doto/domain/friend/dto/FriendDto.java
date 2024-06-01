package site.doto.domain.friend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDto {
    private Long memberId;

    private Long mainCharacterId;
}
