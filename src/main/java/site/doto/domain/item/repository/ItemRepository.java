package site.doto.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.item.entity.Item;
import site.doto.domain.item.entity.ItemPK;

public interface ItemRepository extends JpaRepository<Item, ItemPK> {
}
