package site.doto.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.repository.CategoryRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.domain.todo.dto.TodoAddReq;
import site.doto.domain.todo.dto.TodoDetailsRes;
import site.doto.domain.todo.dto.TodoRedoReq;
import site.doto.domain.todo.entity.Todo;
import site.doto.domain.todo.repository.TodoRepository;
import site.doto.global.exception.CustomException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public TodoDetailsRes addTodo(Long memberId, TodoAddReq todoAddReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Category category = categoryRepository.findById(todoAddReq.getCategoryId())
                .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));

        LocalDate date = toLocalDate(todoAddReq.getDate());

        validateDateRange(date);
        validateActivatedCategory(category.getIsActivated());
        validateMemberCategory(memberId, category.getMember().getId());

        Todo todo = todoAddReq.toEntity(member, category, date);
        member.updateLastUpload(LocalDateTime.now());

        todoRepository.save(todo);
        memberRepository.save(member);
        return TodoDetailsRes.toDto(todo);
    }

    private void validateMemberCategory(Long memberId, Long categoryMemberId) {
        if(!Objects.equals(memberId, categoryMemberId)) {
            throw new CustomException(FORBIDDEN);
        }
    }

    @Transactional
    public void redoTodo(Long memberId, TodoRedoReq todoRedoReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Todo todo = todoRepository.findById(todoRedoReq.getId())
                .orElseThrow(() -> new CustomException(TODO_NOT_FOUND));

        LocalDate date = toLocalDate(todoRedoReq.getDate());

        validateDateRange(date);
        validateMemberTodo(todo.getMember().getId(), member.getId());
        validateActivatedCategory(todo.getCategory().getIsActivated());

        Todo redoTodo = todoRedoReq.toEntity(member, todo.getCategory(), todo, date);

        todoRepository.save(redoTodo);
    }

    private void validateDateRange(LocalDate date) {
        LocalDate startDate = LocalDate.of(2001, 1, 1);
        LocalDate endDate = LocalDate.of(2100, 12, 31);

        if(date.isBefore(startDate) || date.isAfter(endDate)) {
            throw new CustomException(BIND_EXCEPTION);
        }
    }

    private void validateMemberTodo(Long memberId, Long todoMemberId) {
        if(!Objects.equals(memberId, todoMemberId)) {
            throw new CustomException(FORBIDDEN);
        }
    }

    private void validateActivatedCategory(Boolean isActivated) {
        if(!isActivated) {
            throw new CustomException(CATEGORY_INACTIVATED);
        }
    }

    private LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return LocalDate.parse(date , formatter);
    }
}
