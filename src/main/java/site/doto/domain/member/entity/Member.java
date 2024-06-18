package site.doto.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.member.type.MemberType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String description;

    private String mainCharacterImg;

    @Column(name = "coin", columnDefinition = "integer NOT NULL CHECK (coin >= 0)")
    private Integer coin;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MemberType type;

    private LocalDateTime lastUpload;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
