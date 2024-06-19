package site.doto.domain.betting.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.chatroom.entity.ChatRoom;
import site.doto.domain.member.entity.Member;
import site.doto.domain.todo.entity.Todo;

import javax.validation.constraints.NotNull;

@Data
public class BettingAddReq {
    @NotNull
    Long todoId;

    @NotNull
    @Length(min = 2, max = 20)
    String name;

    public Betting toEntity(Member member, Todo todo) {
        ChatRoom chatRoom = new ChatRoom();

        Betting betting = Betting.builder()
                .member(member)
                .todo(todo)
                .chatRoom(chatRoom)
                .name(name)
                .isAchieved(null)
                .build();

        chatRoom.setBetting(betting);

        return betting;
    }
}
