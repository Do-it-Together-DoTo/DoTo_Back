package site.doto.domain.category.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.category.dto.CategoryAddReq;
import site.doto.domain.category.dto.CategoryArrangeReq;
import site.doto.domain.category.dto.CategoryModifyReq;
import site.doto.domain.todo.dto.TodoRedisDto;
import site.doto.global.redis.RedisUtils;

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.doto.global.status_code.ErrorCode.*;
import static site.doto.global.status_code.SuccessCode.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private RedisUtils redisUtils;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("카테고리 등록_성공")
    public void category_add_success() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents("테스트_카테고리");
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("BLUE");

        String content = gson.toJson(categoryAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_CREATED.getMessage()))
                .andExpect(jsonPath("$.body.contents").value("테스트_카테고리"))
                .andExpect(jsonPath("$.body.isPublic").value(true))
                .andExpect(jsonPath("$.body.isActivated").value(true))
                .andExpect(jsonPath("$.body.color").value("BLUE"))
                .andDo(document(
                        "카테고리 등록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Category API")
                                .summary("카테고리 추가 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("contents").type(JsonFieldType.STRING)
                                                        .description("카테고리 내용"),
                                                fieldWithPath("isPublic").type(JsonFieldType.BOOLEAN)
                                                        .description("공개 여부"),
                                                fieldWithPath("color").type(JsonFieldType.STRING)
                                                        .description("카테고리 색상")
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.id").type(JsonFieldType.NUMBER)
                                                        .description("카테고리 ID"),
                                                fieldWithPath("body.contents").type(JsonFieldType.STRING)
                                                        .description("카테고리 내용"),
                                                fieldWithPath("body.isPublic").type(JsonFieldType.BOOLEAN)
                                                        .description("카테고리 공개 여부"),
                                                fieldWithPath("body.isActivated").type(JsonFieldType.BOOLEAN)
                                                        .description("카테고리 활성화 여부"),
                                                fieldWithPath("body.color").type(JsonFieldType.STRING)
                                                        .description("카테고리 색상"),
                                                fieldWithPath("body.seq").type(JsonFieldType.NUMBER)
                                                        .description("카테고리 순서")
                                        )
                                )
                                .requestSchema(Schema.schema("카테고리 등록 Request"))
                                .responseSchema(Schema.schema("카테고리 등록 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("카테고리 전체 조회_성공")
    public void category_list_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORIES_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORIES_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "카테고리 전체 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Category API")
                                .summary("카테고리 전체 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.activated").type(JsonFieldType.ARRAY)
                                                        .description("활성화된 카테고리 목록"),
                                                fieldWithPath("body.inactivated").type(JsonFieldType.ARRAY)
                                                        .description("비활성화된 카테고리 목록"),
                                                fieldWithPath("body.inactivated").type(JsonFieldType.ARRAY)
                                                        .description("비활성화된 카테고리 목록"),
                                                fieldWithPath("body.*[].id").type(JsonFieldType.NUMBER)
                                                        .description("카테고리 ID"),
                                                fieldWithPath("body.*[].contents").type(JsonFieldType.STRING)
                                                        .description("카테고리 내용"),
                                                fieldWithPath("body.*[].isPublic").type(JsonFieldType.BOOLEAN)
                                                        .description("카테고리 공개 여부"),
                                                fieldWithPath("body.*[].isActivated").type(JsonFieldType.BOOLEAN)
                                                        .description("카테고리 활성화 여부"),
                                                fieldWithPath("body.*[].color").type(JsonFieldType.STRING)
                                                        .description("카테고리 색상"),
                                                fieldWithPath("body.*[].seq").type(JsonFieldType.NUMBER)
                                                        .description("카테고리 순서")
                                        )
                                )
                                .responseSchema(Schema.schema("카테고리 전체 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("카테고리 수정_성공")
    public void category_modify_success() throws Exception {
        //given
        CategoryModifyReq categoryModifyReq = new CategoryModifyReq();
        categoryModifyReq.setContents("카테고리1");
        categoryModifyReq.setIsPublic(false);
        categoryModifyReq.setColor("YELLOW");
        categoryModifyReq.setIsActivated(false);

        String content = gson.toJson(categoryModifyReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/categories/{categoryId}", 10001L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_MODIFY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_MODIFY_OK.getMessage()))
                .andExpect(jsonPath("$.body.contents").value("카테고리1"))
                .andExpect(jsonPath("$.body.isPublic").value(false))
                .andExpect(jsonPath("$.body.color").value("YELLOW"))
                .andExpect(jsonPath("$.body.isActivated").value(false))
                .andExpect(jsonPath("$.body.seq").value(2))
                .andDo(document(
                        "카테고리 수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Category API")
                                .summary("카테고리 수정 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(parameterWithName("categoryId").description("카테고리 ID"))
                                .requestFields(
                                        List.of(
                                                fieldWithPath("contents").type(JsonFieldType.STRING)
                                                        .description("카테고리 내용(Optional)"),
                                                fieldWithPath("isPublic").type(JsonFieldType.BOOLEAN)
                                                        .description("공개 여부(Optional)"),
                                                fieldWithPath("color").type(JsonFieldType.STRING)
                                                        .description("카테고리 색상(Optional)"),
                                                fieldWithPath("isActivated").type(JsonFieldType.BOOLEAN)
                                                        .description("활성화 여부(Optional)")
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.id").type(JsonFieldType.NUMBER)
                                                        .description("카테고리 ID"),
                                                fieldWithPath("body.contents").type(JsonFieldType.STRING)
                                                        .description("카테고리 내용"),
                                                fieldWithPath("body.isPublic").type(JsonFieldType.BOOLEAN)
                                                        .description("카테고리 공개 여부"),
                                                fieldWithPath("body.isActivated").type(JsonFieldType.BOOLEAN)
                                                        .description("카테고리 활성화 여부"),
                                                fieldWithPath("body.color").type(JsonFieldType.STRING)
                                                        .description("카테고리 색상"),
                                                fieldWithPath("body.seq").type(JsonFieldType.NUMBER)
                                                        .description("카테고리 순서")
                                        )
                                )
                                .requestSchema(Schema.schema("카테고리 수정 Request"))
                                .responseSchema(Schema.schema("카테고리 수정 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("카테고리 삭제_성공")
    public void category_remove_success() throws Exception {
        //given
        Long categoryId = 10002L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/categories/{categoryId}", categoryId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_DELETED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_DELETED.getMessage()))
                .andDo(document(
                        "카테고리 삭제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Category API")
                                .summary("카테고리 삭제 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body").type(JsonFieldType.NULL)
                                                        .description("내용 없음")
                                        )
                                )
                                .responseSchema(Schema.schema("카테고리 삭제 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("카테고리 순서 변경_성공")
    public void category_arrange_success() throws Exception {
        //given
        CategoryArrangeReq categoryArrangeReq = new CategoryArrangeReq();

        List<Long> categoryIds = new ArrayList<>();
        List<Integer> orders = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            categoryIds.add(10001L + i);
            orders.add(5 - i);
        }

        categoryArrangeReq.setIsActivated(false);
        categoryArrangeReq.setCategoryIds(categoryIds);
        categoryArrangeReq.setOrders(orders);

        String content = gson.toJson(categoryArrangeReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_ARRANGE_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_ARRANGE_OK.getMessage()))
                .andDo(document(
                        "카테고리 순서 변경",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Category API")
                                .summary("카테고리 순서 변경 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("isActivated").type(JsonFieldType.BOOLEAN)
                                                        .description("활성화 여부"),
                                                fieldWithPath("categoryIds").type(JsonFieldType.ARRAY)
                                                        .description("카테고리 ID"),
                                                fieldWithPath("orders").type(JsonFieldType.ARRAY)
                                                        .description("카테고리 순서")
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body").type(JsonFieldType.NULL)
                                                        .description("내용 없음")
                                        )
                                )
                                .requestSchema(Schema.schema("카테고리 순서 변경 Request"))
                                .responseSchema(Schema.schema("카테고리 순서 변경 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("카테고리 등록 실패 - 없는 색상")
    public void category_add_fail_color_not_found() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents("카테고리");
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("RED");

        String content = gson.toJson(categoryAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(COLOR_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(COLOR_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("카테고리 등록 실패 - 활성화된 개수 초과")
    public void category_add_fail_active_category_limit() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents("카테고리");
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("PURPLE");

        String content = gson.toJson(categoryAddReq);

        mockMvc.perform(
                post("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ACTIVATED_CATEGORY_LIMIT.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ACTIVATED_CATEGORY_LIMIT.getMessage()));
    }

    @Test
    @DisplayName("카테고리 등록 실패 - contents is null")
    public void category_add_fail_contents_is_null() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents(null);
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("PURPLE");

        String content = gson.toJson(categoryAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("카테고리 등록 실패 - contents 빈값")
    public void category_add_fail_contents_is_blank() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents("");
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("PURPLE");

        String content = gson.toJson(categoryAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("카테고리 등록 실패 - contetns length 초과")
    public void category_add_fail_contents_limit_length() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents("1234567891011121234444342");
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("PURPLE");

        String content = gson.toJson(categoryAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("카테고리 수정 실패 - 없는 카테고리 수정")
    public void category_modify_fail_category_not_found() throws Exception {
        //given
        CategoryModifyReq categoryModifyReq = new CategoryModifyReq();
        categoryModifyReq.setContents("카테고리 수정1");

        String content = gson.toJson(categoryModifyReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/categories/{categoryId}", 10032L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("카테고리 수정 실패 - 잘못된 contents")
    public void category_modify_fail_contents_is_null() throws Exception {
        //given
        CategoryModifyReq categoryModifyReq = new CategoryModifyReq();
        categoryModifyReq.setContents(" ");

        String content = gson.toJson(categoryModifyReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/categories/{categoryId}", 10001L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("카테고리 수정 실패 - 잘못된 컬러 값")
    public void category_modify_fail_not_found_color() throws Exception {
        CategoryModifyReq categoryModifyReq = new CategoryModifyReq();
        categoryModifyReq.setContents("카테고리 수정");
        categoryModifyReq.setColor("RED");

        String content = gson.toJson(categoryModifyReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/categories/{categoryId}", 10001L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(COLOR_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(COLOR_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("카테고리 수정 실패 - 활성화 개수 초과")
    public void category_modify_fail_active_category_limit() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents("테스트 카테고리 20");
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("PURPLE");

        CategoryModifyReq categoryModifyReq = new CategoryModifyReq();
        categoryModifyReq.setContents("카테고리 수정");
        categoryModifyReq.setColor("Yellow");
        categoryModifyReq.setIsActivated(true);

        String content1 = gson.toJson(categoryAddReq);
        String content2 = gson.toJson(categoryModifyReq);

        //when
        ResultActions actions1 = mockMvc.perform(
                post("/categories/")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content1));

        ResultActions actions2 = mockMvc.perform(
                patch("/categories/{categoryId}", 10020L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content2));

        //then
        actions1
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_CREATED.getMessage()));

        actions2
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ACTIVATED_CATEGORY_LIMIT.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ACTIVATED_CATEGORY_LIMIT.getMessage()));
    }

    @Test
    @DisplayName("카테고리 삭제 실패 - 내 카테고리가 아닌 경우")
    public void category_remove_fail_not_my_category() throws Exception {
        //given
        Long categoryId = 10021L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/categories/{categoryId}", categoryId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FORBIDDEN.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FORBIDDEN.getMessage()));
    }

    @Test
    @DisplayName("카테고리 삭제 실패 - 존재하지 않는 카테고리")
    public void category_remove_fail_not_found_category() throws Exception {
        //given
        Long categoryId = 20000L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/categories/{categoryId}", categoryId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("카테고리 삭제 - redis에 값이 존재하는지 테스트")
    public void category_remove_success_redis_value_present_test() throws Exception {
        //given
        Long categoryId = 10002L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/categories/{categoryId}", categoryId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_DELETED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_DELETED.getMessage()));

        TodoRedisDto savedTodo = redisUtils.findTodo(30005L);
        assertThat(savedTodo).isNotNull();
        assertThat(savedTodo.getBettingId()).isEqualTo(30005L);
        assertThat(savedTodo.getTodoId()).isEqualTo(20007L);
        assertThat(savedTodo.getCategoryId()).isEqualTo(categoryId);
    }

    @Test
    @DisplayName("카테고리 순서 변경 실패 - 두 배열의 개수 안맞음")
    public void category_arrange_fail_array_length_diff() throws Exception {
        //given
        CategoryArrangeReq categoryArrangeReq = new CategoryArrangeReq();
        List<Long> categoryIds = new ArrayList<>();
        List<Integer> orderIds = new ArrayList<>();

        for(int i = 1; i <= 6; i++) {
            categoryIds.add(10000L + i);
        }

        for(int i = 0; i < 5; i++) {
            orderIds.add(i);
        }

        categoryArrangeReq.setIsActivated(false);
        categoryArrangeReq.setCategoryIds(categoryIds);
        categoryArrangeReq.setOrders(orderIds);

        String content = gson.toJson(categoryArrangeReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("카테고리 순서 변경 실패 - 활성화 개수 초과")
    public void category_arrange_fail_active_category_limit() throws Exception {
        //given
        CategoryArrangeReq categoryArrangeReq = new CategoryArrangeReq();
        List<Long> categoryIds = new ArrayList<>();
        List<Integer> orderIds = new ArrayList<>();

        for(int i = 1; i <= 21; i++) {
            categoryIds.add(10000L + i);
            orderIds.add(i-1);
        }

        categoryArrangeReq.setIsActivated(true);
        categoryArrangeReq.setCategoryIds(categoryIds);
        categoryArrangeReq.setOrders(orderIds);

        String content = gson.toJson(categoryArrangeReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/categories")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ACTIVATED_CATEGORY_LIMIT.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ACTIVATED_CATEGORY_LIMIT.getMessage()));
    }
}
