package site.doto.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.betting.repository.BettingRepository;
import site.doto.domain.todo.dto.TodoRedisDto;
import site.doto.domain.todo.entity.Todo;
import site.doto.domain.todo.repository.TodoRepository;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.time.LocalDate;
import java.util.Optional;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final RedisUtils redisUtils;
    private final TodoRepository todoRepository;
    private final BettingRepository bettingRepository;

    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new CustomException(TODO_NOT_FOUND));

        if (!todo.getDate().isBefore(LocalDate.now())) {
            throw new CustomException(DELETE_NOT_ALLOWED);
        }

        Optional<Betting> betting = bettingRepository.findBettingByTodoId(todoId);

        if (betting.isPresent()) {
            TodoRedisDto todoRedisDto = TodoRedisDto.toDto(todo, betting.get().getId());
            redisUtils.saveTodo(todoRedisDto);
            betting.get().todoDisconnected();
        }

        todoRepository.delete(todo);
    }
}
