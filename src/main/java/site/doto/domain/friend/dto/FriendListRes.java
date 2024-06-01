package site.doto.domain.friend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.doto.domain.friend.entity.Friend;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FriendListRes {
    List<FriendDto> friends = new ArrayList<>();

}
