package site.doto.domain.character_type.entity;

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
    private long id;

    private String name;

    private String img;

    private int level;

    private int species;

}
