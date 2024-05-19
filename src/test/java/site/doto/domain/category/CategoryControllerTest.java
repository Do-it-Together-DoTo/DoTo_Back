package site.doto.domain.category;

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

import java.util.ArrayList;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("카테고리 등록_성공")
    public void category_add_success() throws Exception {
        //given
        CategoryAddReq categoryAddReq = new CategoryAddReq();
        categoryAddReq.setContents("카테고리");
        categoryAddReq.setIsPublic(true);
        categoryAddReq.setColor("blue");

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
                .andExpect(jsonPath("$.body.contents").value("카테고리"))
                .andExpect(jsonPath("$.body.isPublic").value(true))
                .andExpect(jsonPath("$.body.isActivated").value(true))
                .andExpect(jsonPath("$.body.color").value("blue"))
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
        categoryModifyReq.setContents("카테고리");
        categoryModifyReq.setIsPublic(true);
        categoryModifyReq.setColor("blue");

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
                                                        .description("카테고리 색상(Optional)")
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

        //when
        ResultActions actions = mockMvc.perform(
                delete("/categories/{categoryId}", 10001L)
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

        for (int i = 1; i < 5; i++) {
            categoryIds.add(10000L + i);
            orders.add(6 - i);
        }

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
}
