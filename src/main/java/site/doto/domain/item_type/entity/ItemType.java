package site.doto.domain.item_type.entity;

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
public class ItemType {
    @Id
    @Column(name = "item_type_id")
    private Long id;

    private String name;

    private String img;

    private Integer price;

    private String grade;

    private Integer exp;

    private String description;

}
