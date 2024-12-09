package site.doto.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.repository.CategoryRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.domain.todo.dto.*;
import site.doto.domain.todo.entity.Todo;
import site.doto.domain.todo.repository.TodoRepository;
import site.doto.global.exception.CustomException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

        validateDateRange(todoAddReq.getDate());
        validateActivatedCategory(category.getIsActivated());
        validateMemberCategory(memberId, category.getMember().getId());

        Todo todo = todoAddReq.toEntity(member, category);
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

        validateDateRange(todoRedoReq.getDate());
        validateMemberTodo(todo.getMember().getId(), member.getId());
        validateActivatedCategory(todo.getCategory().getIsActivated());

        Todo redoTodo = todoRedoReq.toEntity(member, todo.getCategory(), todo);

        todoRepository.save(redoTodo);
    }

    @Transactional
    public void changeDoneTodo(Long memberId, Long todoId, Boolean isDone) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new CustomException(TODO_NOT_FOUND));

        validateMemberTodo(todo.getMember().getId(), member.getId());

        todo.updateIsDone(isDone);

        todoRepository.save(todo);
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

    public MyTodoListRes findTodos(Long memberId, TodoListReq todoListReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        validateDateRange(todoListReq.getDate());

        MyTodoListRes result = new MyTodoListRes();
        List<Category> categoryList;

        if(todoListReq.getDate().isBefore(LocalDate.now())) {
            categoryList = categoryRepository.findCategoriesByTodoIsPresent(memberId, todoListReq.getDate());
        } else {
            categoryList = categoryRepository.findCategoriesByMemberIdAndIsActivated(memberId);
        }

        for(Category category: categoryList) {
            List<TodoDetailsRes> todoList = todoRepository.findTodoDetailsByCategory(category.getId(), todoListReq.getDate());
            MyTodoCategoryDto todoCategoryDto = MyTodoCategoryDto.toDto(category, todoList);
            result.getTodoList().add(todoCategoryDto);
        }

        return result;
    }
}
