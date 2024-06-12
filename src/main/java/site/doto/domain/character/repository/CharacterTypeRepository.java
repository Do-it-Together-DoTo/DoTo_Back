package site.doto.domain.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.character.entity.CharacterType;

import java.util.Optional;

public interface CharacterTypeRepository extends JpaRepository<CharacterType, Long> {
    Optional<CharacterType> findById(Long characterTypeId);
}
