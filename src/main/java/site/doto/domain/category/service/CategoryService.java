package site.doto.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.category.dto.CategoryAddReq;
import site.doto.domain.category.dto.CategoryDetailsRes;
import site.doto.domain.category.dto.CategoryListRes;
import site.doto.domain.category.entity.Category;
import site.doto.domain.category.enums.Color;
import site.doto.domain.category.repository.CategoryRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.exception.CustomException;

import java.util.List;

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

        if(!EnumUtils.isValidEnumIgnoreCase(Color.class, categoryAddReq.getColor())) {
            throw new CustomException(COLOR_NOT_FOUND);
        }

        int activeCount = calculateActiveCount(memberId);
        if(activeCount >= MAX_ACTIVE_COUNT) {
            throw new CustomException(ACTIVATED_CATEGORY_LIMIT);
        }

        Category category = categoryAddReq.toEntity(member, activeCount);
        categoryRepository.save(category);

        return CategoryDetailsRes.toDto(category);
    }

    public CategoryListRes listCategory(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        List<Category> activatedList = categoryRepository.findCategoriesByIsActivated(memberId);
        List<Category> inactivedList = categoryRepository.findCategoriesByIsInactivated(memberId);

        return new CategoryListRes(activatedList, inactivedList);
    }

    private int calculateActiveCount(Long memberId) {
        return categoryRepository.countByMemberId(memberId);
    }
}
