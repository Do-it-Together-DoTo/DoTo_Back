package site.doto.domain.friend.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendPK implements Serializable {
    private Long toMemberId;
    private Long fromMemberId;
}
