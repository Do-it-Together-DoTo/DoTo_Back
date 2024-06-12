package site.doto.domain.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.doto.domain.character.entity.Character;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findById(Long characterId);
}
