package site.doto.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.item.entity.ItemType;

import java.util.Optional;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
    Optional<ItemType> findById(Long itemTypeId);
}
