package site.doto.domain.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.member.entity.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item implements Serializable {
    @EmbeddedId
    private ItemPK itemPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("itemTypeId")
    private ItemType itemType;

    private Integer count;

}
