package site.doto.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.category.dto.*;
import site.doto.domain.category.service.CategoryService;
import site.doto.global.dto.ResponseDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static site.doto.domain.category.enums.Color.*;
import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseDto<CategoryDetailsRes> categoryAdd(
            @RequestBody @Valid CategoryAddReq categoryAddReq) {
        Long memberId = 1L;

        CategoryDetailsRes result = categoryService.addCategory(memberId, categoryAddReq);

        return ResponseDto.success(CATEGORY_CREATED, result);
    }

    @GetMapping
    public ResponseDto<CategoryListRes> categoryList() {
        Long memberId = 1L;

        CategoryListRes result = categoryService.listCategory(memberId);

        return ResponseDto.success(CATEGORIES_INQUIRY_OK, result);
    }

    @PatchMapping("/{categoryId}")
    public ResponseDto<CategoryDetailsRes> categoryModify(
            @PathVariable long categoryId,
            @RequestBody CategoryModifyReq categoryModifyReq) {
        Long memberId = 1L;

        CategoryDetailsRes result = categoryService.modifyCategory(memberId, categoryId, categoryModifyReq);

        return ResponseDto.success(CATEGORY_MODIFY_OK, result);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseDto<?> categoryRemove(
            @PathVariable long categoryId) {

        return ResponseDto.success(CATEGORY_DELETED, null);
    }

    @PatchMapping
    public ResponseDto<?> categoryArrange(
            @RequestBody CategoryArrangeReq categoryArrangeReq) {

        return ResponseDto.success(CATEGORY_ARRANGE_OK, null);
    }
}
