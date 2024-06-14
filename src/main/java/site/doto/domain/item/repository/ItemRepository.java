package site.doto.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import site.doto.domain.item.entity.Item;
import site.doto.domain.item.entity.ItemPK;

public interface ItemRepository extends JpaRepository<Item, ItemPK> {
    @Modifying
    @Query("update Item i set i.count = i.count + :count where i.itemPK.memberId = :memberId and i.itemPK.itemTypeId = :itemTypeId")
    int updateItemCount(@Param("memberId") Long memberId, @Param("itemTypeId") Long itemTypeId, @Param("count") int count);
}
