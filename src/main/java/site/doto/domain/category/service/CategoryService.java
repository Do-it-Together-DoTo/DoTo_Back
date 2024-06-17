package site.doto.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.category.dto.CategoryAddReq;
import site.doto.domain.category.dto.CategoryDetailsRes;
import site.doto.domain.category.dto.CategoryListRes;
import site.doto.domain.category.dto.CategoryModifyReq;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.category.repository.CategoryRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.exception.CustomException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static site.doto.global.status_code.ErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private static final int MAX_ACTIVE_COUNT = 20;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

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

        updateCategory(memberId, category, categoryModifyReq);

        categoryRepository.save(category);

        return CategoryDetailsRes.toDto(category);
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

    private void updateCategory(Long memberId, Category category, CategoryModifyReq categoryModifyReq) {
        updateContents(category, categoryModifyReq.getContents());
        updateIsPublic(category, categoryModifyReq.getIsPublic());
        updateIsActivated(memberId, category, categoryModifyReq.getIsActivated());
        updateColor(category, categoryModifyReq.getColor());
    }

    private void updateContents(Category category, String contents) {
        if(contents != null) {
            if(contents.trim().isEmpty()) {
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
