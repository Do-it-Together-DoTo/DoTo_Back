package site.doto.domain.friend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import site.doto.global.dto.SliceDto;

@Data
@NoArgsConstructor
public class FriendListRes {
    SliceDto<FriendDto> friends;

    public FriendListRes(Slice<FriendDto> friendDtoSlice) {
        friends = new SliceDto<>(friendDtoSlice);
    }
}
