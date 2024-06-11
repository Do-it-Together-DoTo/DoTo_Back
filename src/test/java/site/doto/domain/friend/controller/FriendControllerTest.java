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
        friendRequestReq.setFromMemberId(2L);

        String content = gson.toJson(friendRequestReq);

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
                                        List.of(
                                                fieldWithPath("fromMemberId").type(JsonFieldType.NUMBER)
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
        friendResponseReq.setFromMemberId(2L);

        String content = gson.toJson(friendResponseReq);

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
                                        List.of(
                                                fieldWithPath("fromMemberId").type(JsonFieldType.NUMBER)
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
        friendDeclinedReq.setFromMemberId(2L);

        String content = gson.toJson(friendDeclinedReq);

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
                                        List.of(
                                                fieldWithPath("fromMemberId").type(JsonFieldType.NUMBER)
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
                delete("/friends/{memberId}", 2L)
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
                                .pathParameters(parameterWithName("memberId").description("친구 Id"))
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
                get("/friends")
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
                                                        .description("마지막 친구 Id (Optional)").optional(),
                                                fieldWithPath("lastFriendTodoDate").type(JsonFieldType.STRING)
                                                        .description("마지막 친구의 Todo 생성 시간 (Optional)").optional()
                                        )
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
                                .requestSchema(Schema.schema("친구 목록 Request"))
                                .responseSchema(Schema.schema("친구 목록 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("친구 차단 목록 성공")
    public void friend_block_list_success() throws Exception {
        // given
        FriendBlockListReq friendBlockListReq = new FriendBlockListReq();
        friendBlockListReq.setLastFriendId(1L);

        String content = gson.toJson(friendBlockListReq);

        // when
        ResultActions actions = mockMvc.perform(
                get("/friends/block")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
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
                                .requestFields(
                                        List.of(
                                                fieldWithPath("lastFriendId").type(JsonFieldType.NUMBER)
                                                        .description("마지막 친구 Id (Optional)").optional()
                                        )
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
                                .requestSchema(Schema.schema("친구 차단 목록 Request"))
                                .responseSchema(Schema.schema("친구 차단 목록 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("유저 차단 성공")
    public void friend_block_success() throws Exception {
        // given
        FriendBlockReq friendBlockReq = new FriendBlockReq();
        friendBlockReq.setMemberId(2L);

        String content = gson.toJson(friendBlockReq);

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
        FriendUnblockReq friendUnblockReq = new FriendUnblockReq();
        friendUnblockReq.setMemberId(2L);

        String content = gson.toJson(friendUnblockReq);

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
