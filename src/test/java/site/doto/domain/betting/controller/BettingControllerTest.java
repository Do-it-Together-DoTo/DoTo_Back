package site.doto.domain.betting.controller;

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
import site.doto.domain.betting.dto.BettingAddReq;
import site.doto.domain.betting.dto.BettingJoinReq;

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
class BettingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("베팅 생성 - 성공")
    public void betting_add_success() throws Exception {
        //given
        ResultActions remove = mockMvc.perform(
                delete("/betting/{bettingId}", 30001L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));


        BettingAddReq bettingAddReq = new BettingAddReq();
        bettingAddReq.setTodoId(20001L);
        bettingAddReq.setName("베팅 이름");
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_CREATED.getMessage()))
                .andDo(document(
                        "베팅 생성",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Betting API")
                                .summary("베팅 생성 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        fieldWithPath("todoId").type(JsonFieldType.NUMBER)
                                                .description("투두 ID"),
                                        fieldWithPath("name").type(JsonFieldType.STRING)
                                                .description("베팅 이름")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body").type(JsonFieldType.NULL)
                                                .description("내용 없음")
                                )
                                .requestSchema(Schema.schema("베팅 생성 Request"))
                                .responseSchema(Schema.schema("베팅 생성 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 생성 - 검증 실패")
    public void betting_add_validation_fail() throws Exception {
        //given
        BettingAddReq bettingAddReq1 = new BettingAddReq();
        bettingAddReq1.setName("베팅 이름");
        String content1 = gson.toJson(bettingAddReq1);

        BettingAddReq bettingAddReq2 = new BettingAddReq();
        bettingAddReq2.setTodoId(20001L);
        bettingAddReq2.setName("베팅 이름 too looooooooooooooooooooooooooooooooooooooooooooooooooong");
        String content2 = gson.toJson(bettingAddReq2);

        //when
        ResultActions notEnoughRequestFields = mockMvc.perform(
                post("/betting")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content1));

        ResultActions wrongBettingName = mockMvc.perform(
                post("/betting")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content2));

        //then
        notEnoughRequestFields
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));

        wrongBettingName
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("베팅 생성 - 존재하지 않는 투두")
    public void betting_add_todo_not_found() throws Exception {
        //given
        BettingAddReq bettingAddReq = new BettingAddReq();
        bettingAddReq.setTodoId(21001L);
        bettingAddReq.setName("베팅 이름");
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("베팅 생성 - 다른 사람의 투두")
    public void betting_add_todo_not_mine() throws Exception {
        //given
        BettingAddReq bettingAddReq = new BettingAddReq();
        bettingAddReq.setTodoId(20002L);
        bettingAddReq.setName("베팅 이름");
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FORBIDDEN.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FORBIDDEN.getMessage()));
    }

    @Test
    @DisplayName("베팅 생성 - 과거의 투두")
    public void betting_add_todo_already_past() throws Exception {
        //given
        BettingAddReq bettingAddReq = new BettingAddReq();
        bettingAddReq.setTodoId(20003L);
        bettingAddReq.setName("베팅 이름");
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_ALREADY_PAST.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_ALREADY_PAST.getMessage()));
    }

    @Test
    @DisplayName("베팅 생성 - 이미 완료된 투두")
    public void betting_add_todo_already_done() throws Exception {
        //given
        BettingAddReq bettingAddReq = new BettingAddReq();
        bettingAddReq.setTodoId(20004L);
        bettingAddReq.setName("베팅 이름");
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(TODO_ALREADY_DONE.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(TODO_ALREADY_DONE.getMessage()));
    }

    @Test
    @DisplayName("베팅 참여 - 성공")
    public void betting_join_success() throws Exception {
        //given
        Long bettingId = 30004L;
        BettingJoinReq bettingAddReq = new BettingJoinReq();
        bettingAddReq.setCost(50);
        bettingAddReq.setPrediction(true);
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_JOIN_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_JOIN_CREATED.getMessage()))
                .andDo(document(
                        "베팅 참여",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Betting API")
                                .summary("베팅 참여 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("bettingId").description("베팅 ID")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("cost").type(JsonFieldType.NUMBER)
                                                        .description("베팅 금액"),
                                                fieldWithPath("prediction").type(JsonFieldType.BOOLEAN)
                                                        .description("성공 예측")
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
                                .requestSchema(Schema.schema("베팅 참여 Request"))
                                .responseSchema(Schema.schema("베팅 참여 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 참여 - 검증 실패")
    public void betting_join_validation_fail() throws Exception {
        //given
        Long bettingId = 30004L;

        BettingJoinReq bettingAddReq1 = new BettingJoinReq();
        bettingAddReq1.setCost(50);
        String content1 = gson.toJson(bettingAddReq1);

        BettingJoinReq bettingAddReq2 = new BettingJoinReq();
        bettingAddReq2.setCost(0);
        bettingAddReq2.setPrediction(true);
        String content2 = gson.toJson(bettingAddReq2);

        BettingJoinReq bettingAddReq3 = new BettingJoinReq();
        bettingAddReq3.setCost(100);
        bettingAddReq3.setPrediction(true);
        String content3 = gson.toJson(bettingAddReq3);

        //when
        ResultActions notEnoughRequestFields = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content1));

        ResultActions tooLittleBet = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content2));

        ResultActions tooMuchBet = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content3));

        //then
        notEnoughRequestFields
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));

        tooLittleBet
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));

        tooMuchBet
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("베팅 참여 - 존재하지 않는 베팅")
    public void betting_join_not_found() throws Exception {
        //given
        Long bettingId = 31001L;
        BettingJoinReq bettingAddReq = new BettingJoinReq();
        bettingAddReq.setCost(50);
        bettingAddReq.setPrediction(true);
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("베팅 참여 - 자신의 베팅에 참여")
    public void betting_join_betting_self_join() throws Exception {
        //given
        Long bettingId = 30001L;
        BettingJoinReq bettingAddReq = new BettingJoinReq();
        bettingAddReq.setCost(50);
        bettingAddReq.setPrediction(true);
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_SELF_JOIN.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_SELF_JOIN.getMessage()));
    }

    @Test
    @DisplayName("베팅 참여 - 친구가 아닌 유저의 베팅")
    public void betting_join_betting_not_friend() throws Exception {
        //given
        Long bettingId = 30006L;
        BettingJoinReq bettingAddReq = new BettingJoinReq();
        bettingAddReq.setCost(50);
        bettingAddReq.setPrediction(true);
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(NOT_FRIEND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(NOT_FRIEND.getMessage()));
    }

    @Test
    @DisplayName("베팅 참여 - 종료된 베팅")
    public void betting_join_betting_closed() throws Exception {
        //given
        Long bettingId = 30003L;
        BettingJoinReq bettingAddReq = new BettingJoinReq();
        bettingAddReq.setCost(50);
        bettingAddReq.setPrediction(true);
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_CLOSED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_CLOSED.getMessage()));
    }

    @Test
    @DisplayName("베팅 참여 - 이미 참여한 베팅")
    public void betting_join_already_joining() throws Exception {
        //given
        Long bettingId = 30002L;
        BettingJoinReq bettingAddReq = new BettingJoinReq();
        bettingAddReq.setCost(50);
        bettingAddReq.setPrediction(true);
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_ALREADY_JOINING.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_ALREADY_JOINING.getMessage()));
    }

    @Test
    @DisplayName("나의 베팅 조회 - 성공")
    public void my_betting_list_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/betting")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTINGS_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTINGS_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "나의 베팅 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Betting API")
                                .summary("나의 베팅 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body.myBetting").type(JsonFieldType.ARRAY)
                                                .description("내가 연 베팅"),
                                        fieldWithPath("body.myBetting[].memberId").type(JsonFieldType.NUMBER)
                                                .description("멤버 ID"),
                                        fieldWithPath("body.myBetting[].mainCharacterImg").type(JsonFieldType.STRING)
                                                .description("대표 캐릭터 이미지"),
                                        fieldWithPath("body.myBetting[].memberNickname").type(JsonFieldType.STRING)
                                                .description("멤버 닉네임"),
                                        fieldWithPath("body.myBetting[].bettingId").type(JsonFieldType.NUMBER)
                                                .description("베팅 ID"),
                                        fieldWithPath("body.myBetting[].bettingName").type(JsonFieldType.STRING)
                                                .description("베팅 이름"),
                                        fieldWithPath("body.joiningBetting").type(JsonFieldType.ARRAY)
                                                .description("참여한 베팅 목록"),
                                        fieldWithPath("body.joiningBetting[].memberId").type(JsonFieldType.NUMBER)
                                                .description("멤버 ID").optional(),
                                        fieldWithPath("body.joiningBetting[].mainCharacterImg").type(JsonFieldType.STRING)
                                                .description("대표 캐릭터 이미지").optional(),
                                        fieldWithPath("body.joiningBetting[].memberNickname").type(JsonFieldType.STRING)
                                                .description("멤버 닉네임").optional(),
                                        fieldWithPath("body.joiningBetting[].bettingId").type(JsonFieldType.NUMBER)
                                                .description("베팅 ID").optional(),
                                        fieldWithPath("body.joiningBetting[].bettingName").type(JsonFieldType.STRING)
                                                .description("베팅 이름").optional(),
                                        fieldWithPath("body.closedBetting").type(JsonFieldType.ARRAY)
                                                .description("종료된 베팅 목록"),
                                        fieldWithPath("body.closedBetting[].memberId").type(JsonFieldType.NUMBER)
                                                .description("멤버 ID").optional(),
                                        fieldWithPath("body.closedBetting[].mainCharacterImg").type(JsonFieldType.STRING)
                                                .description("대표 캐릭터 이미지").optional(),
                                        fieldWithPath("body.closedBetting[].memberNickname").type(JsonFieldType.STRING)
                                                .description("멤버 닉네임").optional(),
                                        fieldWithPath("body.closedBetting[].bettingId").type(JsonFieldType.NUMBER)
                                                .description("베팅 ID").optional(),
                                        fieldWithPath("body.closedBetting[].bettingName").type(JsonFieldType.STRING)
                                                .description("베팅 이름").optional()
                                )
                                .responseSchema(Schema.schema("나의 베팅 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("오픈 베팅 조회 - 성공")
    public void open_betting_list_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/betting/open")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTINGS_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTINGS_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "오픈 베팅 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Betting API")
                                .summary("오픈 베팅 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.openBetting").type(JsonFieldType.ARRAY)
                                                        .description("참여한 베팅 목록"),
                                                fieldWithPath("body.openBetting[].memberId").type(JsonFieldType.NUMBER)
                                                        .description("멤버 ID"),
                                                fieldWithPath("body.openBetting[].mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("대표 캐릭터 이미지"),
                                                fieldWithPath("body.openBetting[].memberNickname").type(JsonFieldType.STRING)
                                                        .description("멤버 닉네임"),
                                                fieldWithPath("body.openBetting[].bettingId").type(JsonFieldType.NUMBER)
                                                        .description("베팅 ID"),
                                                fieldWithPath("body.openBetting[].bettingName").type(JsonFieldType.STRING)
                                                        .description("베팅 이름")
                                        )
                                )
                                .responseSchema(Schema.schema("오픈 베팅 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 단일 조회 - 성공")
    public void betting_details_success() throws Exception {
        //given
        Long bettingId = 30001L;

        //when
        ResultActions actions = mockMvc.perform(
                get("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "베팅 단일 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Betting API")
                                .summary("베팅 단일 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("bettingId").description("베팅 ID")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body.bettingId").type(JsonFieldType.NUMBER)
                                                .description("베팅 ID"),
                                        fieldWithPath("body.bettingName").type(JsonFieldType.STRING)
                                                .description("베팅 이름"),
                                        fieldWithPath("body.memberId").type(JsonFieldType.NUMBER)
                                                .description("멤버 ID"),
                                        fieldWithPath("body.memberNickname").type(JsonFieldType.STRING)
                                                .description("멤버 닉네임"),
                                        fieldWithPath("body.todoContents").type(JsonFieldType.STRING)
                                                .description("투두 내용"),
                                        fieldWithPath("body.successCoins").type(JsonFieldType.NUMBER)
                                                .description("성공에 베팅된 코인"),
                                        fieldWithPath("body.failureCoins").type(JsonFieldType.NUMBER)
                                                .description("실패에 베팅된 코인"),
                                        fieldWithPath("body.participantCount").type(JsonFieldType.NUMBER)
                                                .description("베팅 참여 인원"),
                                        fieldWithPath("body.isParticipating").type(JsonFieldType.BOOLEAN)
                                                .description("베팅 참가 여부"),
                                        fieldWithPath("body.chatRoomId").type(JsonFieldType.NUMBER)
                                                .description("채팅방 ID"),
                                        fieldWithPath("body.isFinished").type(JsonFieldType.BOOLEAN)
                                                .description("채팅방 ID").optional(),
                                        fieldWithPath("body.isAchieved").type(JsonFieldType.BOOLEAN)
                                                .description("채팅방 ID").optional()
                                )
                                .responseSchema(Schema.schema("베팅 단일 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 단일 조회 - 존재하지 않는 베팅")
    public void betting_details_betting_not_found() throws Exception {
        //given
        Long bettingId = 100001L;

        //when
        ResultActions actions = mockMvc.perform(
                get("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("베팅 삭제 - 성공")
    public void betting_remove_success() throws Exception {
        //given
        Long bettingId = 30001L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_DELETED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_DELETED.getMessage()))
                .andDo(document(
                        "베팅 삭제",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Betting API")
                                .summary("베팅 삭제 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("bettingId").description("베팅 ID")
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
                                .responseSchema(Schema.schema("베팅 삭제 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 삭제 - 존재하지 않는 베팅")
    public void betting_remove_not_found() throws Exception {
        //given
        Long bettingId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(BETTING_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BETTING_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("베팅 삭제 - 다른 사람의 베팅")
    public void betting_remove_forbidden() throws Exception {
        //given
        Long bettingId = 30002L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/betting/{bettingId}", bettingId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(FORBIDDEN.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(FORBIDDEN.getMessage()));
    }

    @Test
    @DisplayName("베팅 삭제 - 다른 사람이 참여 중인 베팅")
    public void betting_remove_cancel_fail() throws Exception {
        // 베팅 참여 api 구현 후 추가 예정
    }
}
