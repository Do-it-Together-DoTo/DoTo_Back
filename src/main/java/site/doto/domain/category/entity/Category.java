package site.doto.domain.category.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.category.enums.Scope;
import site.doto.domain.category.enums.Color;
import site.doto.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String contents;

    @Enumerated(EnumType.STRING)
    private Scope scope;

    private Boolean isActivated;

    @Enumerated(EnumType.STRING)
    private Color color;

    private Integer seq;

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void updateScope(Scope scope) {
        this.scope = scope;
    }

    public void updateIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public void updateColor(Color color) {
        this.color = color;
    }

    public void updateSeq(Integer seq) {
        this.seq = seq+1;
    }
}
