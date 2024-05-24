package site.doto.domain.friend.controller;

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
import site.doto.domain.friend.dto.*;
import site.doto.domain.friend.enums.FriendRelation;

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
class FriendControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("친구 신청 성공")
    public void friend_request_success() throws Exception {
        // given
        FriendRequestReq friendRequestReq = new FriendRequestReq();
        friendRequestReq.setToMemberId(2L);
        friendRequestReq.setStatus(FriendRelation.WAITING);

        String content = gson.toJson(friendRequestReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/members/friends/request")
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
                                        List.of(
                                                fieldWithPath("toMemberId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id"),
                                                fieldWithPath("status").type(JsonFieldType.STRING)
                                                        .description("친구 상태 코드")
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
                                .requestSchema(Schema.schema("친구 신청 Request"))
                                .responseSchema(Schema.schema("친구 신청 Response"))
                                .build()
                        ))
                );
    }
    
    @Test
    @DisplayName("친구 신청 수락 성공")
    public void friend_response_success() throws Exception {
        // given
        FriendResponseReq friendResponseReq = new FriendResponseReq();
        friendResponseReq.setToMemberId(2L);

        String content = gson.toJson(friendResponseReq);

        // when
        ResultActions actions = mockMvc.perform(
                post("/members/friends/response")
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
                                        List.of(
                                                fieldWithPath("toMemberId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id")
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
                                .requestSchema(Schema.schema("친구 신청 수락 Request"))
                                .responseSchema(Schema.schema("친구 신청 수락 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 신청 거절 성공")
    public void friend_declined_success() throws Exception {
        // given
        FriendDeclinedReq friendDeclinedReq = new FriendDeclinedReq();
        friendDeclinedReq.setToMemberId(2L);

        String content = gson.toJson(friendDeclinedReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/members/friends/response")
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
                                        List.of(
                                                fieldWithPath("toMemberId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id")
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
                                .requestSchema(Schema.schema("친구 신청 거절 Request"))
                                .responseSchema(Schema.schema("친구 신청 거절 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 신청 취소 성공")
    public void friend_canceled_success() throws Exception {
        // given
        FriendCanceledReq friendCanceledReq = new FriendCanceledReq();
        friendCanceledReq.setToMemberId(2L);

        String content = gson.toJson(friendCanceledReq);

        // when
        ResultActions actions = mockMvc.perform(
                delete("/members/friends/request")
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
                                        List.of(
                                                fieldWithPath("toMemberId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id")
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
                                .requestSchema(Schema.schema("친구 신청 취소 Request"))
                                .responseSchema(Schema.schema("친구 신청 취소 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 삭제 성공")
    public void friend_remove_success() throws Exception {
        // given

        // when
        ResultActions actions = mockMvc.perform(
                delete("/members/friends/{toMemberId}", 2L)
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
                                .pathParameters(parameterWithName("toMemberId").description("친구 Id"))
                                .responseSchema(Schema.schema("친구 삭제 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 목록 성공")
    public void friend_List_success() throws Exception {
        // given
        FriendListReq friendListReq = new FriendListReq();
        friendListReq.setLastFriendId(2L);
        friendListReq.setLastFriendTodoDate("2024-05-19 00:00:00");

        String content = gson.toJson(friendListReq);

        // when
        ResultActions actions = mockMvc.perform(
                get("/members/friends")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
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
                                .requestFields(
                                        List.of(
                                                fieldWithPath("lastFriendId").type(JsonFieldType.NUMBER)
                                                        .description("마지막 친구 Id"),
                                                fieldWithPath("lastFriendTodoDate").type(JsonFieldType.STRING)
                                                        .description("마지막 친구의 Todo 생성 시간")
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.friends").type(JsonFieldType.ARRAY)
                                                        .description("친구 목록"),
                                                fieldWithPath("body.*[].fromMemberId").type(JsonFieldType.NUMBER)
                                                        .description("유저 Id"),
                                                fieldWithPath("body.*[].toMemberId").type(JsonFieldType.NUMBER)
                                                        .description("친구 Id"),
                                                fieldWithPath("body.*[].status").type(JsonFieldType.STRING)
                                                        .description("친구 상태 코드")
                                        )
                                )
                                .requestSchema(Schema.schema("친구 목록 Request"))
                                .responseSchema(Schema.schema("친구 목록 Response"))
                                .build()
                        ))
                );
    }
}