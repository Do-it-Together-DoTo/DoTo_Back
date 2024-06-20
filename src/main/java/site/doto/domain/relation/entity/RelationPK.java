package site.doto.domain.relation.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationPK implements Serializable {
    private Long memberId;
    private Long friendId;
}
