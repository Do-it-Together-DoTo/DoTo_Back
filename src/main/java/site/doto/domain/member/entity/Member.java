package site.doto.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.member.type.MemberType;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "coin", nullable = false, columnDefinition = "integer CHECK (coin >= 0)")
    private Integer coin;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MemberType type;

    private LocalDateTime lastUpload;

}
