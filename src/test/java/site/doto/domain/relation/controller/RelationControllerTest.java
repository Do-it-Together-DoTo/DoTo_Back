package site.doto.domain.relation.controller;

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
import site.doto.domain.relation.dto.*;

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
import static site.doto.global.status_code.ErrorCode.*;
import static site.doto.global.status_code.SuccessCode.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class RelationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("친구 신청 - 성공")
    public void friend_request_success() throws Exception {
        // given
        RelationRequestReq relationRequestReq = new RelationRequestReq();
        relationRequestReq.setFriendId(30000L);

        String content = gson.toJson(relationRequestReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_REQUEST_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_REQUEST_CREATED.getMessage()))
                .andDo(document(
                        "친구 신청",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("친구 신청 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        fieldWithPath("friendId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body").type(JsonFieldType.NULL)
                                                .description("내용 없음")
                                )
                                .requestSchema(Schema.schema("친구 신청 Request"))
                                .responseSchema(Schema.schema("친구 신청 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 신청 - 존재하지 않는 사용자에게 친구 신청")
    public void friend_request_member_not_found() throws Exception {
        // given
        RelationRequestReq relationRequestReq = new RelationRequestReq();
        relationRequestReq.setFriendId(10000L);

        String content = gson.toJson(relationRequestReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 - 자기 자신에게 친구 신청")
    public void friend_request_self_request() throws Exception {
        // given
        RelationRequestReq relationRequestReq = new RelationRequestReq();
        relationRequestReq.setFriendId(1L);

        String content = gson.toJson(relationRequestReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_SELF_REQUEST.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_SELF_REQUEST.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 - 차단한 사용자에게 친구 신청")
    public void friend_request_blocked_member() throws Exception {
        // given
        RelationRequestReq relationRequestReq = new RelationRequestReq();
        relationRequestReq.setFriendId(20001L);

        String content = gson.toJson(relationRequestReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BLOCKED_MEMBER.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BLOCKED_MEMBER.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 - 이미 친구 신청한 사용자에게 친구 신청")
    public void friend_request_already_requesting() throws Exception {
        // given
        RelationRequestReq relationRequestReq = new RelationRequestReq();
        relationRequestReq.setFriendId(20002L);

        String content = gson.toJson(relationRequestReq);

        // when
        ResultActions preActions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        ResultActions actions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_REQUEST_COOLDOWN.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_REQUEST_COOLDOWN.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 - 이미 친구인 사용자에게 친구 신청")
    public void friend_request_already_add() throws Exception {
        // given
        RelationRequestReq relationRequestReq = new RelationRequestReq();
        relationRequestReq.setFriendId(20000L);

        String content = gson.toJson(relationRequestReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_ALREADY_ADDED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_ALREADY_ADDED.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 - 차단당한 사용자에게 친구 신청")
    public void friend_request_block_member() throws Exception {
        // given
        RelationRequestReq relationRequestReq = new RelationRequestReq();
        relationRequestReq.setFriendId(20003L);

        String content = gson.toJson(relationRequestReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 수락 - 성공")
    public void friend_response_success() throws Exception {
        // given
        RelationResponseReq relationResponseReq = new RelationResponseReq();
        relationResponseReq.setFriendId(20004L);

        String content = gson.toJson(relationResponseReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_CREATED.getMessage()))
                .andDo(document(
                        "친구 신청 수락",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("친구 신청 수락 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        fieldWithPath("friendId").type(JsonFieldType.NUMBER)
                                                .description("친구 Id")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body").type(JsonFieldType.NULL)
                                                .description("내용 없음")
                                )
                                .requestSchema(Schema.schema("친구 신청 수락 Request"))
                                .responseSchema(Schema.schema("친구 신청 수락 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 신청 수락 - 존재하지 않는 사용자에게서 온 친구 신청")
    public void friend_response_member_not_found() throws Exception {
        // given
        RelationResponseReq relationResponseReq = new RelationResponseReq();
        relationResponseReq.setFriendId(10000L);

        String content = gson.toJson(relationResponseReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 수락 - 존재하지 않는 친구 신청")
    public void friend_response_friend_request_missing() throws Exception {
        // given
        RelationResponseReq relationResponseReq = new RelationResponseReq();
        relationResponseReq.setFriendId(30000L);

        String content = gson.toJson(relationResponseReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_REQUEST_MISSING.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_REQUEST_MISSING.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 수락 - 이미 추가된 친구")
    public void friend_response_friend_already_added() throws Exception {
        // given
        RelationResponseReq relationResponseReq = new RelationResponseReq();
        relationResponseReq.setFriendId(2L);

        String content = gson.toJson(relationResponseReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_ALREADY_ADDED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_ALREADY_ADDED.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 수락 - 차단당한 사용자에게서 온 친구 신청")
    public void friend_response_block_member() throws Exception {
        // given
        RelationResponseReq relationResponseReq = new RelationResponseReq();
        relationResponseReq.setFriendId(20003L);

        String content = gson.toJson(relationResponseReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 거절 - 성공")
    public void friend_declined_success() throws Exception {
        // given
        RelationDeclinedReq relationDeclinedReq = new RelationDeclinedReq();
        relationDeclinedReq.setFriendId(20004L);

        String content = gson.toJson(relationDeclinedReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_REQUEST_DELETED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_REQUEST_DELETED.getMessage()))
                .andDo(document(
                        "친구 신청 거절",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("친구 신청 거절 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        fieldWithPath("friendId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body").type(JsonFieldType.NULL)
                                                .description("내용 없음")
                                )
                                .requestSchema(Schema.schema("친구 신청 거절 Request"))
                                .responseSchema(Schema.schema("친구 신청 거절 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 신청 거절 - 존재하지 않는 사용자에게서 온 친구 신청")
    public void friend_declined_member_not_found() throws Exception {
        // given
        RelationDeclinedReq relationDeclinedReq = new RelationDeclinedReq();
        relationDeclinedReq.setFriendId(10000L);

        String content = gson.toJson(relationDeclinedReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 거절 - 존재하지 않는 친구 신청")
    public void friend_declined_friend_request_missing() throws Exception {
        // given
        RelationDeclinedReq relationDeclinedReq = new RelationDeclinedReq();
        relationDeclinedReq.setFriendId(30000L);

        String content = gson.toJson(relationDeclinedReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_REQUEST_MISSING.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_REQUEST_MISSING.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 거절 - 이미 추가된 친구")
    public void friend_declined_friend_already_added() throws Exception {
        // given
        RelationDeclinedReq relationDeclinedReq = new RelationDeclinedReq();
        relationDeclinedReq.setFriendId(2L);

        String content = gson.toJson(relationDeclinedReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_ALREADY_ADDED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_ALREADY_ADDED.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 거절 - 차단당한 사용자에게서 온 친구 신청")
    public void friend_declined_block_member() throws Exception {
        // given
        RelationDeclinedReq relationDeclinedReq = new RelationDeclinedReq();
        relationDeclinedReq.setFriendId(20003L);

        String content = gson.toJson(relationDeclinedReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/response")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 취소 - 성공")
    public void friend_canceled_success() throws Exception {
        // given
        RelationCanceledReq relationCanceledReq = new RelationCanceledReq();
        relationCanceledReq.setFriendId(20006L);

        String content = gson.toJson(relationCanceledReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_REQUEST_CANCELED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_REQUEST_CANCELED.getMessage()))
                .andDo(document(
                        "친구 신청 취소",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("친구 신청 취소 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        fieldWithPath("friendId").type(JsonFieldType.NUMBER)
                                                .description("친구 Id")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body").type(JsonFieldType.NULL)
                                                .description("내용 없음")
                                )
                                .requestSchema(Schema.schema("친구 신청 취소 Request"))
                                .responseSchema(Schema.schema("친구 신청 취소 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 신청 취소 - 존재하지 않는 사용자에게 친구 신청")
    public void friend_canceled_member_not_found() throws Exception {
        // given
        RelationCanceledReq relationCanceledReq = new RelationCanceledReq();
        relationCanceledReq.setFriendId(10000L);

        String content = gson.toJson(relationCanceledReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 취소 - 존재하지 않는 친구 신청")
    public void friend_canceled_friend_request_missing() throws Exception {
        // given
        RelationCanceledReq relationCanceledReq = new RelationCanceledReq();
        relationCanceledReq.setFriendId(30000L);

        String content = gson.toJson(relationCanceledReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_REQUEST_MISSING.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_REQUEST_MISSING.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 취소 - 이미 추가된 친구")
    public void friend_canceled_friend_already_added() throws Exception {
        // given
        RelationCanceledReq relationCanceledReq = new RelationCanceledReq();
        relationCanceledReq.setFriendId(2L);

        String content = gson.toJson(relationCanceledReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_ALREADY_ADDED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_ALREADY_ADDED.getMessage()));
    }

    @Test
    @DisplayName("친구 신청 취소 - 차단한 사용자에게 보낸 친구 신청")
    public void friend_canceled_blocked_member() throws Exception {
        // given
        RelationCanceledReq relationCanceledReq = new RelationCanceledReq();
        relationCanceledReq.setFriendId(20007L);

        String content = gson.toJson(relationCanceledReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/request")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BLOCKED_MEMBER.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BLOCKED_MEMBER.getMessage()));
    }

    @Test
    @DisplayName("친구 삭제 - 성공")
    public void friend_remove_success() throws Exception {
        // given
        Long friendId = 2L;

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/{friendId}", friendId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_DELETED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_DELETED.getMessage()))
                .andDo(document(
                        "친구 삭제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("친구 삭제 API")
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
                                .pathParameters(parameterWithName("friendId").description("친구 Id"))
                                .responseSchema(Schema.schema("친구 삭제 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 삭제 - 존재하지 않는 사용자와의 친구 삭제")
    public void friend_remove_member_not_found() throws Exception {
        // given
        Long friendId = 10000L;

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/{friendId}", friendId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("친구 삭제 - 차단한 사용자")
    public void friend_remove_blocked_member() throws Exception {
        // given
        Long friendId = 20009L;

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/{friendId}", friendId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BLOCKED_MEMBER.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BLOCKED_MEMBER.getMessage()));
    }

    @Test
    @DisplayName("친구 목록 성공")
    public void friend_List_success() throws Exception {
        // given
        Long lastFriendId = 1L;
        String lastFriendTodoDate = "2024-05-19 00:00:00";

        // when
        ResultActions actions = mockMvc.perform(
                get("/friends")
                        .header("Authorization", jwtToken)
                        .param("lastFriendId", String.valueOf(lastFriendId))
                        .param("lastFriendTodoDate", lastFriendTodoDate)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIENDS_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIENDS_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "친구 목록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("친구 목록 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestParameters(
                                        parameterWithName("lastFriendId").description("마지막 친구 Id (Optional)").optional(),
                                        parameterWithName("lastFriendTodoDate").description("마지막 친구의 Todo 생성 시간 (Optional)").optional()
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.friends.content").type(JsonFieldType.ARRAY)
                                                        .description("친구 목록"),
                                                fieldWithPath("body.friends.*[].memberId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id"),
                                                fieldWithPath("body.friends.*[].nickname").type(JsonFieldType.STRING)
                                                        .description("친구 닉네임"),
                                                fieldWithPath("body.friends.*[].mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("친구 메인 캐릭터 이미지"),
                                                fieldWithPath("body.friends.sliceNumber").type(JsonFieldType.NUMBER)
                                                        .description("슬라이스 번호"),
                                                fieldWithPath("body.friends.size").type(JsonFieldType.NUMBER)
                                                        .description("슬라이스 크기"),
                                                fieldWithPath("body.friends.hasNext").type(JsonFieldType.BOOLEAN)
                                                        .description("다음 슬라이스 여부"),
                                                fieldWithPath("body.friends.numberOfElements").type(JsonFieldType.NUMBER)
                                                        .description("요소의 수")
                                        )
                                )
                                .responseSchema(Schema.schema("친구 목록 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 차단 목록 성공")
    public void friend_block_list_success() throws Exception {
        // given
        Long lastFriendId = 1L;

        // when
        ResultActions actions = mockMvc.perform(
                get("/friends/block")
                        .header("Authorization", jwtToken)
                        .param("lastFriendId", String.valueOf(lastFriendId))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_BLOCK_LIST_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_BLOCK_LIST_OK.getMessage()))
                .andDo(document(
                        "친구 차단 목록",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("친구 차단 목록 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestParameters(
                                        parameterWithName("lastFriendId").description("마지막 친구 Id (Optional)").optional()
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.friends.content").type(JsonFieldType.ARRAY)
                                                        .description("차단된 친구 목록"),
                                                fieldWithPath("body.friends.*[].memberId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id"),
                                                fieldWithPath("body.friends.*[].nickname").type(JsonFieldType.STRING)
                                                        .description("친구 닉네임"),
                                                fieldWithPath("body.friends.*[].mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("친구 메인 캐릭터 이미지"),
                                                fieldWithPath("body.friends.sliceNumber").type(JsonFieldType.NUMBER)
                                                        .description("슬라이스 번호"),
                                                fieldWithPath("body.friends.size").type(JsonFieldType.NUMBER)
                                                        .description("슬라이스 크기"),
                                                fieldWithPath("body.friends.hasNext").type(JsonFieldType.BOOLEAN)
                                                        .description("다음 슬라이스 여부"),
                                                fieldWithPath("body.friends.numberOfElements").type(JsonFieldType.NUMBER)
                                                        .description("요소의 수")
                                        )
                                )
                                .responseSchema(Schema.schema("친구 차단 목록 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("유저 차단 성공")
    public void friend_block_success() throws Exception {
        // given
        RelationBlockReq relationBlockReq = new RelationBlockReq();
        relationBlockReq.setMemberId(2L);

        String content = gson.toJson(relationBlockReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/friends/block")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_BLOCK_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_BLOCK_OK.getMessage()))
                .andDo(document(
                        "유저 차단",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("유저 차단 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("memberId").type(JsonFieldType.NUMBER)
                                                        .description("유저 Id")
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
                                .requestSchema(Schema.schema("유저 차단 Request"))
                                .responseSchema(Schema.schema("유저 차단 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("유저 차단 취소 성공")
    public void friend_unblock_success() throws Exception {
        // given
        RelationUnblockReq relationUnblockReq = new RelationUnblockReq();
        relationUnblockReq.setMemberId(2L);

        String content = gson.toJson(relationUnblockReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/friends/block")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FRIEND_UNBLOCK_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FRIEND_UNBLOCK_OK.getMessage()))
                .andDo(document(
                        "유저 차단 취소",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Friend API")
                                .summary("유저 차단 취소 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("memberId").type(JsonFieldType.NUMBER)
                                                        .description("유저 Id")
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
                                .requestSchema(Schema.schema("유저 차단 취소 Request"))
                                .responseSchema(Schema.schema("유저 차단 취소 Response"))
                                .build()
                        ))
                );
    }
}
