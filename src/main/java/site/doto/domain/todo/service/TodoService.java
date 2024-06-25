package site.doto.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.betting.repository.BettingRepository;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.repository.CategoryRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.domain.todo.dto.TodoAddReq;
import site.doto.domain.todo.dto.TodoDetailsRes;
import site.doto.domain.todo.dto.TodoRedisDto;
import site.doto.domain.todo.entity.Todo;
import site.doto.domain.todo.repository.TodoRepository;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.util.Objects;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final RedisUtils redisUtils;
    private final TodoRepository todoRepository;
    private final BettingRepository bettingRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public TodoDetailsRes addTodo(Long memberId, TodoAddReq todoAddReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Category category = categoryRepository.findById(todoAddReq.getCategoryId())
                .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));

        validateActivatedCategory(category.getIsActivated());
        validateMemberCategory(memberId, category.getMember().getId());

        Todo todo = todoAddReq.toEntity(member, category);

        todoRepository.save(todo);

        return TodoDetailsRes.toDto(todo);
    }

    @Transactional
    public void disconnectBetting(Todo todo) {
        Betting betting = bettingRepository.findBettingByTodo(todo);

        if(betting.getIsAchieved() == null) {
            throw new CustomException(DELETE_NOT_ALLOWED);
        }

        TodoRedisDto todoRedisDto = TodoRedisDto.toDto(todo);
        saveTodoToRedis(todoRedisDto, betting.getId());

        betting.todoDisconnected();
        bettingRepository.save(betting);
    }

    private void saveTodoToRedis(TodoRedisDto dto, Long bettingId) {
        String key = "todo:" + bettingId;

        redisUtils.setData(key, dto);
    }

    private void validateActivatedCategory(Boolean isActivated) {
        if(!isActivated) {
            throw new CustomException(CATEGORY_INACTIVATED);
        }
    }

    private void validateMemberCategory(Long memberId, Long categoryMemberId) {
        if(!Objects.equals(memberId, categoryMemberId)) {
            throw new CustomException(FORBIDDEN);
        }
    }
}
