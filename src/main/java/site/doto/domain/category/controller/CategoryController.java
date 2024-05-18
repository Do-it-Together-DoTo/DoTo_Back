package site.doto.domain.category.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.category.dto.*;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @PostMapping
    public ResponseDto<CategoryDetailsRes> categoryAdd(
            @RequestBody CategoryAddReq categoryAddReq) {
        CategoryDetailsRes result = CategoryDetailsRes.builder()
                .id(10001L)
                .contents(categoryAddReq.getContents())
                .isPublic(categoryAddReq.getIsPublic())
                .isActivated(true)
                .color(categoryAddReq.getColor())
                .seq(1)
                .build();

        return ResponseDto.success(CATEGORY_CREATED, result);
    }

    @GetMapping
    public ResponseDto<CategoryListRes> categoryList() {
        List<CategoryDetailsRes> activated = new ArrayList<>();
        List<CategoryDetailsRes> inactivated = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            activated.add(CategoryDetailsRes.builder()
                    .id(10000L + i)
                    .contents("Mock Category" + i)
                    .isPublic(true)
                    .isActivated(true)
                    .color(i % 2 == 0 ? "blue" : "pink")
                    .seq(i)
                    .build());
        }

        for (int i = 11; i <= 20; i++) {
            inactivated.add(CategoryDetailsRes.builder()
                    .id(10000L + i)
                    .contents("Mock Category" + i)
                    .isPublic(true)
                    .isActivated(false)
                    .color(i % 2 == 0 ? "yellow" : "green")
                    .seq(i)
                    .build());
        }

        CategoryListRes result = new CategoryListRes();
        result.setActivated(activated);
        result.setInactivated(inactivated);

        return ResponseDto.success(CATEGORIES_INQUIRY_OK, result);
    }

    @PatchMapping("/{categoryId}")
    public ResponseDto<CategoryDetailsRes> categoryModify(
            @PathVariable long categoryId,
            @RequestBody CategoryModifyReq categoryModifyReq) {
        CategoryDetailsRes result = CategoryDetailsRes.builder()
                .id(10001L)
                .contents("Modified Mock Category")
                .isActivated(true)
                .isPublic(true)
                .color("pink")
                .seq(1)
                .build();

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
