package site.doto.domain.betting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.doto.domain.chatroom.entity.ChatRoom;
import site.doto.domain.member.entity.Member;
import site.doto.domain.todo.entity.Todo;

import javax.persistence.*;
import java.time.LocalDate;

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

    @OneToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String name;

    private Boolean isAchieved;

    public LocalDate getDate() {
        return todo.getDate();
    }

    public void todoDisconnected() {
        this.todo = null;
    }
}
