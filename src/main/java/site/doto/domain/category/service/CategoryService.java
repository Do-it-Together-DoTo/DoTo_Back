package site.doto.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.betting.repository.BettingRepository;
import site.doto.domain.category.dto.CategoryAddReq;
import site.doto.domain.category.dto.CategoryDetailsRes;
import site.doto.domain.category.dto.CategoryListRes;
import site.doto.domain.category.dto.CategoryModifyReq;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.category.repository.CategoryRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.domain.todo.entity.Todo;
import site.doto.domain.todo.repository.TodoRepository;
import site.doto.domain.todo.service.TodoService;
import site.doto.global.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static site.doto.global.status_code.ErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private static final int MAX_ACTIVE_COUNT = 20;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final TodoService todoService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CategoryDetailsRes addCategory(Long memberId, CategoryAddReq categoryAddReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        validateColor(categoryAddReq.getColor());

        Integer activeCount = calculateSequence(memberId, true);
        validateActiveCount(activeCount);

        Category category = categoryAddReq.toEntity(member, activeCount);
        categoryRepository.save(category);

        return CategoryDetailsRes.toDto(category);
    }

    public CategoryListRes listCategory(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        List<Category> categoryList = categoryRepository.findCategoriesByMemberId(memberId);

        Map<Boolean, List<Category>> partitionedMap = categoryList.stream()
                .collect(Collectors.partitioningBy(Category::getIsActivated));

        List<Category> activatedList = partitionedMap.get(true);
        List<Category> inactivedList = partitionedMap.get(false);

        return new CategoryListRes(activatedList, inactivedList);
    }

    @Transactional
    public CategoryDetailsRes modifyCategory(Long memberId, Long categoryId, CategoryModifyReq categoryModifyReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));

        validateMemberCategory(memberId, category.getMember().getId());

        updateCategory(memberId, category, categoryModifyReq);

        categoryRepository.save(category);

        return CategoryDetailsRes.toDto(category);
    }

    @Transactional
    public void removeCategory(Long memberId, Long categoryId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Category category =  categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));

        validateMemberCategory(memberId, category.getMember().getId());

        List<Todo> todoList = todoRepository.findTodoIfExistBetting(category);

        if(!todoList.isEmpty()) {
            for (Todo todo : todoList) {
                todoService.deleteTodo(todo);
            }
            entityManager.flush();
        }

        todoRepository.deleteByCategoryId(categoryId);
        categoryRepository.delete(category);
    }

    private Integer calculateSequence(Long memberId, Boolean isActivated) {
        return categoryRepository.categorySeqByMemberId(memberId, isActivated);
    }

    private void validateColor(String color) {
        if(!EnumUtils.isValidEnumIgnoreCase(Color.class, color)) {
            throw new CustomException(COLOR_NOT_FOUND);
        }
    }

    private void validateActiveCount(Integer activeCount) {
        if(activeCount >= MAX_ACTIVE_COUNT) {
            throw new CustomException(ACTIVATED_CATEGORY_LIMIT);
        }
    }

    private void validateMemberCategory(Long memberId, Long categoryMemberId) {
        if(!Objects.equals(memberId, categoryMemberId)) {
            throw new CustomException(FORBIDDEN);
        }
    }

    private void updateCategory(Long memberId, Category category, CategoryModifyReq categoryModifyReq) {
        updateContents(category, categoryModifyReq.getContents());
        updateIsPublic(category, categoryModifyReq.getIsPublic());
        updateIsActivated(memberId, category, categoryModifyReq.getIsActivated());
        updateColor(category, categoryModifyReq.getColor());
    }

    private void updateContents(Category category, String contents) {
        if(contents != null) {
            if(contents.isBlank()) {
                throw new CustomException(BIND_EXCEPTION);
            }
            category.updateContents(contents);
        }
    }

    private void updateIsPublic(Category category, Boolean isPublic) {
        if(isPublic != null) {
            category.updateIsPublic(isPublic);
        }
    }

    private void updateIsActivated(Long memberId, Category category, Boolean isActivated) {
        if(isActivated != null) {
            if(isActivated) {
                int activeCount = calculateSequence(memberId, true);
                validateActiveCount(activeCount);
            }

            if(category.getIsActivated() != isActivated) {
                Integer seq = calculateSequence(memberId, isActivated);
                category.updateSeq(seq);
            }
            category.updateIsActivated(isActivated);
        }
    }

    private void updateColor(Category category, String color) {
        if(color != null) {
            validateColor(color);
            category.updateColor(Color.valueOf(color.toUpperCase()));
        }
    }
}
