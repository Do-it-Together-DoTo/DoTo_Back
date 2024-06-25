package site.doto.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.category.dto.*;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.category.enums.Scope;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static site.doto.domain.category.enums.Scope.PRIVATE;
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
        validateScope(categoryAddReq.getScope());

        Integer activeCount = calculateSequence(memberId, true);
        validateActiveCount(activeCount);

        Category category = categoryAddReq.toEntity(member, activeCount);
        categoryRepository.save(category);

        return CategoryDetailsRes.toDto(category);
    }

    public CategoryListRes findCategory(Long memberId) {
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
                todoService.disconnectBetting(todo);
            }
            entityManager.flush();
        }

        todoRepository.deleteByCategoryId(categoryId);
        categoryRepository.delete(category);
    }

    @Transactional
    public void arrangeCategory(Long memberId, CategoryArrangeReq categoryArrangeReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        List<Category> categoryList = categoryRepository.findCategoriesByMemberId(memberId);

        List<Long> activatedList = categoryArrangeReq.getActivated();
        List<Long> inactivatedList = categoryArrangeReq.getInactivated();

        int length = activatedList.size() + inactivatedList.size();

        if(categoryList.size() != length) {
            throw new CustomException(NOT_MATCH_CATEGORIES);
        }

        if(duplicatedLong(activatedList, inactivatedList)) {
            throw new CustomException(BIND_EXCEPTION);
        }

        validateActiveCount(activatedList.size()-1);

        updateSeq(memberId, activatedList, true);
        updateSeq(memberId, inactivatedList, false);
    }


    private Integer calculateSequence(Long memberId, Boolean isActivated) {
        return categoryRepository.categorySeqByMemberId(memberId, isActivated);
    }

    private void validateScope(String scope) {
        if(!EnumUtils.isValidEnumIgnoreCase(Scope.class, scope)) {
            throw new CustomException(SCOPE_NOT_FOUND);
        }
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

    private boolean duplicatedLong(List<Long> activated, List<Long> inactivated) {
        Set<Long> uniqueSet = Stream.concat(activated.stream(), inactivated.stream())
                .collect(Collectors.toSet());

        return uniqueSet.size() < (activated.size() + inactivated.size());
    }

    private void updateCategory(Long memberId, Category category, CategoryModifyReq categoryModifyReq) {
        updateContents(category, categoryModifyReq.getContents());
        updateScope(category, categoryModifyReq.getScope());
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

    private void updateScope(Category category, String scopeName) {
        if(scopeName != null) {
            validateScope(scopeName);

            Scope scope = Scope.valueOf(scopeName.toUpperCase());
            if(scope.equals(PRIVATE)) {
                Todo todo = todoRepository.findTodoIfOngoingBetting(category);
                if(todo != null) {
                    throw new CustomException(CATEGORY_CHANGE_RESTRICTED);
                }
            }
            category.updateScope(scope);
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

    private void updateSeq(Long memberId, List<Long> ids, Boolean isActivated) {
        IntStream.range(0, ids.size())
                .forEach(index -> {
                    Long categoryId = ids.get(index);
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new CustomException(CATEGORY_NOT_FOUND));

                    validateMemberCategory(memberId, category.getMember().getId());

                    category.updateIsActivated(isActivated);
                    category.updateSeq(index);

                    categoryRepository.save(category);
                });
    }
}
