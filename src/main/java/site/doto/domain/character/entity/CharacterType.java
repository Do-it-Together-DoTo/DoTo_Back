package site.doto.domain.character.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

}
