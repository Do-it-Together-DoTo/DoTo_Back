package site.doto.domain.betting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.member.entity.Member;
import site.doto.domain.todo.entity.Todo;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Betting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "betting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    private String name;

    private Boolean isAchieved;

}
