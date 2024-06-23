package site.doto.domain.todo.controller;

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
import site.doto.domain.todo.dto.TodoAddReq;
import site.doto.domain.todo.dto.TodoModifyReq;
import site.doto.domain.todo.dto.TodoRedoReq;

import java.time.LocalDate;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.doto.global.status_code.ErrorCode.*;
import static site.doto.global.status_code.SuccessCode.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("Todo 생성 성공")
    public void todo_add_success() throws Exception {
        // given
        TodoAddReq todoAddReq = new TodoAddReq();
        todoAddReq.setCategoryId(10001L);
        todoAddReq.setContents("투두 생성");

        String content = gson.toJson(todoAddReq);

        // when
        ResultActions actions = mockMvc.perform(
             post("/todo")
                     .header("Authorization", jwtToken)
                     .accept(MediaType.APPLICATION_JSON)
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_CRATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_CRATED.getMessage()))
                .andExpect(jsonPath("$.body.id").value(20009L))
                .andExpect(jsonPath("$.body.contents").value("투두 생성"))
                .andExpect(jsonPath("$.body.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.body.isDone").value(false))
                .andDo(document(
                        "Todo 생성",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Todo API")
                                .summary("Todo 생성 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("categoryId").type(JsonFieldType.NUMBER)
                                                        .description("카테고리 Id"),
                                                fieldWithPath("contents").type(JsonFieldType.STRING)
                                                        .description("Todo 내용")
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.id").type(JsonFieldType.NUMBER)
                                                        .description("Todo Id"),
                                                fieldWithPath("body.contents").type(JsonFieldType.STRING)
                                                        .description("Todo 내용"),
                                                fieldWithPath("body.date").type(JsonFieldType.STRING)
                                                        .description("Todo 생성 날짜"),
                                                fieldWithPath("body.isDone").type(JsonFieldType.BOOLEAN)
                                                        .description("Todo 완료 여부")
                                        )
                                )
                                .requestSchema(Schema.schema("Todo 생성 Request"))
                                .responseSchema(Schema.schema("Todo 생성 Response"))
                                .build()
                        ))
                );

    }

    @Test
    @DisplayName("Todo 전체 조회 성공")
    public void todo_list_success() throws Exception {
        // given
        long memberId = 1L;
        String date = "2024-05-19 00:00:00";

        // when
        ResultActions actions = mockMvc.perform(
                get("/todo/{memberId}", memberId)
                        .header("Authorization", jwtToken)
                        .param("date", date)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "Todo 전체 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Todo API")
                                .summary("Todo 전체 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("memberId").description("회원 Id")
                                )
                                .requestParameters(
                                        parameterWithName("date").description("해당 날짜")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body.todoList[].categoryId").type(JsonFieldType.NUMBER)
                                                .description("카테고리 Id"),
                                        fieldWithPath("body.todoList[].categoryContents").type(JsonFieldType.STRING)
                                                .description("카테고리 내용"),
                                        fieldWithPath("body.todoList[].categoryIsActivated").type(JsonFieldType.BOOLEAN)
                                                .description("카테고리 활성화 여부"),
                                        fieldWithPath("body.todoList[].categoryColor").type(JsonFieldType.STRING)
                                                .description("카테고리 색상"),
                                        fieldWithPath("body.todoList[].*[].id").type(JsonFieldType.NUMBER)
                                                .description("Todo Id"),
                                        fieldWithPath("body.todoList[].*[].contents").type(JsonFieldType.STRING)
                                                .description("Todo 내용"),
                                        fieldWithPath("body.todoList[].*[].date").type(JsonFieldType.STRING)
                                                .description("Todo 날짜"),
                                        fieldWithPath("body.todoList[].*[].isDone").type(JsonFieldType.BOOLEAN)
                                                .description("Todo 완료 여부")
                                )
                                .responseSchema(Schema.schema("Todo 전체 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("Todo 수정 성공")
    public void todo_modify_success() throws Exception {
        // given
        long todoId = 1L;
        TodoModifyReq todoModifyReq = new TodoModifyReq();
        todoModifyReq.setContents("Todo 수정");
        todoModifyReq.setDate("2024-05-19 00:00:00");

        String content = gson.toJson(todoModifyReq);

        // when
        ResultActions actions = mockMvc.perform(
                patch("/todo/{todoId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_MODIFY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_MODIFY_OK.getMessage()))
                .andDo(document(
                        "Todo 수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Todo API")
                                .summary("Todo 수정 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("todoId").description("Todo Id")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("contents").type(JsonFieldType.STRING)
                                                        .description("Todo 변경 내용(Optional)").optional(),
                                                fieldWithPath("date").type(JsonFieldType.STRING)
                                                        .description("Todo 변경 날짜(Optional)").optional()
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.id").type(JsonFieldType.NUMBER)
                                                        .description("Todo Id"),
                                                fieldWithPath("body.contents").type(JsonFieldType.STRING)
                                                        .description("Todo 내용"),
                                                fieldWithPath("body.date").type(JsonFieldType.STRING)
                                                        .description("Todo 생성 날짜"),
                                                fieldWithPath("body.isDone").type(JsonFieldType.BOOLEAN)
                                                        .description("Todo 완료 여부")
                                        )
                                )
                                .requestSchema(Schema.schema("Todo 수정 Request"))
                                .responseSchema(Schema.schema("Todo 수정 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("Todo 삭제 성공")
    public void todo_remove_success() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(
                delete("/todo/{todoId}", 10001L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_DELETED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_DELETED.getMessage()))
                .andDo(document(
                        "Todo 삭제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Todo API")
                                .summary("Todo 삭제 API")
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
                                .responseSchema(Schema.schema("Todo 삭제 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("Todo 완료 여부 성공")
    public void todo_change_done_success() throws Exception {
        // given
        long todoId = 1L;

        // when
        ResultActions actions = mockMvc.perform(
                patch("/todo/check/{todoId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_CHECK_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_CHECK_OK.getMessage()))
                .andDo(document(
                        "Todo 완료 여부",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Todo API")
                                .summary("Todo 완료 여부 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("todoId").description("Todo Id")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.id").type(JsonFieldType.NUMBER)
                                                        .description("Todo Id"),
                                                fieldWithPath("body.contents").type(JsonFieldType.STRING)
                                                        .description("Todo 내용"),
                                                fieldWithPath("body.date").type(JsonFieldType.STRING)
                                                        .description("Todo 생성 날짜"),
                                                fieldWithPath("body.isDone").type(JsonFieldType.BOOLEAN)
                                                        .description("Todo 완료 여부")
                                        )
                                )
                                .responseSchema(Schema.schema("Todo 완료 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("Todo 또하기 성공")
    public void todo_redo_success() throws Exception {
        // given
        TodoRedoReq todoRedoReq = new TodoRedoReq();
        todoRedoReq.setId(1L);
        todoRedoReq.setDate("2024-05-19 00:00:00");

        String content = gson.toJson(todoRedoReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/todo/date")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_RE_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_RE_CREATED.getMessage()))
                .andDo(document(
                        "Todo 또하기",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Todo API")
                                .summary("Todo 또하기 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("id").type(JsonFieldType.NUMBER)
                                                        .description("Todo id"),
                                                fieldWithPath("date").type(JsonFieldType.STRING)
                                                        .description("Todo 생성 날짜")
                                        )
                                )
                                .requestSchema(Schema.schema("Todo 또하기 Request"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("투두 생성 실패 - contents 빈 값")
    public void todo_add_fail_contents_is_empty() throws Exception {
        // given
        TodoAddReq todoAddReq = new TodoAddReq();
        todoAddReq.setCategoryId(10001L);
        todoAddReq.setContents(" ");

        String content = gson.toJson(todoAddReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/todo")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));

    }

    @Test
    @DisplayName("투두 생성 실패 - contents is null")
    public void todo_add_fail_contents_is_null() throws Exception {
        // given
        TodoAddReq todoAddReq = new TodoAddReq();
        todoAddReq.setCategoryId(10001L);
        todoAddReq.setContents(null);

        String content = gson.toJson(todoAddReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/todo")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));

    }

    @Test
    @DisplayName("투두 생성 실패 - categoryId is null")
    public void todo_add_fail_category_id_is_null() throws Exception {
        // given
        TodoAddReq todoAddReq = new TodoAddReq();
        todoAddReq.setCategoryId(null);
        todoAddReq.setContents("투두 생성 테스트");

        String content = gson.toJson(todoAddReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/todo")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));

    }

    @Test
    @DisplayName("투두 생성 실패 - 없는 카테고리 값")
    public void todo_add_fail_not_exist_category() throws Exception {
        // given
        TodoAddReq todoAddReq = new TodoAddReq();
        todoAddReq.setCategoryId(10038L);
        todoAddReq.setContents("투두 생성 테스트");

        String content = gson.toJson(todoAddReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/todo")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_NOT_FOUND.getMessage()));

    }

    @Test
    @DisplayName("투두 생성 실패 - 비활성 카테고리에 투두 생성")
    public void todo_add_fail_category_is_in_activated() throws Exception {
        // given
        TodoAddReq todoAddReq = new TodoAddReq();
        todoAddReq.setCategoryId(10020L);
        todoAddReq.setContents("투두 생성 테스트");

        String content = gson.toJson(todoAddReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/todo")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CATEGORY_INACTIVATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CATEGORY_INACTIVATED.getMessage()));

    }

    @Test
    @DisplayName("투두 생성 실패 - 본인 카테고리가 아닌 경우")
    public void todo_add_fail_category_is_not_mine() throws Exception {
        // given
        TodoAddReq todoAddReq = new TodoAddReq();
        todoAddReq.setCategoryId(10022L);
        todoAddReq.setContents("투두 생성 테스트");

        String content = gson.toJson(todoAddReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/todo")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FORBIDDEN.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FORBIDDEN.getMessage()));

    }
}
