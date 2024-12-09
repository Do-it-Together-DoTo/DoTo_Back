package site.doto.domain.character.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterType {
    @Id
    @Column(name = "character_type_id")
    private Long id;

    private String name;

    private String img;

    private Integer level;

    private Integer species;

    private String description;

}
