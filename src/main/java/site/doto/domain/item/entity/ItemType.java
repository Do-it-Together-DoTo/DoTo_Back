package site.doto.domain.item.entity;

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
