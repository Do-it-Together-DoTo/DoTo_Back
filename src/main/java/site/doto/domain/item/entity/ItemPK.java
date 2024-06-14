package site.doto.domain.item.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPK implements Serializable {
    private Long memberId;
    private Long itemTypeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPK itemPK = (ItemPK) o;
        return Objects.equals(memberId, itemPK.memberId) && Objects.equals(itemTypeId, itemPK.itemTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, itemTypeId);
    }
}
