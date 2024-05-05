package site.doto.domain.friend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.member.entity.Member;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Friend implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member fromMember;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Member toMember;

    private int status;

}
