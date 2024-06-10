package site.doto.domain.betting;

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
    @DisplayName("베팅 생성_성공")
    public void betting_add_success() throws Exception {
        //given
        BettingAddReq bettingAddReq = new BettingAddReq();
        bettingAddReq.setTodoId(10001L);
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
                                        List.of(
                                                fieldWithPath("todoId").type(JsonFieldType.NUMBER)
                                                        .description("투두 ID"),
                                                fieldWithPath("name").type(JsonFieldType.STRING)
                                                        .description("베팅 이름")
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
                                .requestSchema(Schema.schema("베팅 생성 Request"))
                                .responseSchema(Schema.schema("베팅 생성 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 참여_성공")
    public void betting_join_success() throws Exception {
        //given
        BettingJoinReq bettingAddReq = new BettingJoinReq();
        bettingAddReq.setCost(50);
        bettingAddReq.setPrediction(true);
        String content = gson.toJson(bettingAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/betting/{bettingId}", 10001L)
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
    @DisplayName("나의 베팅 조회_성공")
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
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.myBetting").type(JsonFieldType.OBJECT)
                                                        .description("내가 연 베팅(Optional)").optional(),
                                                fieldWithPath("body.myBetting.memberId").type(JsonFieldType.NUMBER)
                                                        .description("멤버 ID"),
                                                fieldWithPath("body.myBetting.mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("대표 캐릭터 이미지"),
                                                fieldWithPath("body.myBetting.memberNickname").type(JsonFieldType.STRING)
                                                        .description("멤버 닉네임"),
                                                fieldWithPath("body.myBetting.bettingId").type(JsonFieldType.NUMBER)
                                                        .description("베팅 ID"),
                                                fieldWithPath("body.myBetting.bettingName").type(JsonFieldType.STRING)
                                                        .description("베팅 이름"),
                                                fieldWithPath("body.joiningBetting").type(JsonFieldType.ARRAY)
                                                        .description("참여한 베팅 목록"),
                                                fieldWithPath("body.closedBetting").type(JsonFieldType.ARRAY)
                                                        .description("종료된 베팅 목록"),
                                                fieldWithPath("body.*[].memberId").type(JsonFieldType.NUMBER)
                                                        .description("멤버 ID"),
                                                fieldWithPath("body.*[].mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("대표 캐릭터 이미지"),
                                                fieldWithPath("body.*[].memberNickname").type(JsonFieldType.STRING)
                                                        .description("멤버 닉네임"),
                                                fieldWithPath("body.*[].bettingId").type(JsonFieldType.NUMBER)
                                                        .description("베팅 ID"),
                                                fieldWithPath("body.*[].bettingName").type(JsonFieldType.STRING)
                                                        .description("베팅 이름")
                                        )
                                )
                                .responseSchema(Schema.schema("나의 베팅 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("오픈 베팅 조회_성공")
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
                                                fieldWithPath("body.*[].memberId").type(JsonFieldType.NUMBER)
                                                        .description("멤버 ID"),
                                                fieldWithPath("body.*[].mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("대표 캐릭터 이미지"),
                                                fieldWithPath("body.*[].memberNickname").type(JsonFieldType.STRING)
                                                        .description("멤버 닉네임"),
                                                fieldWithPath("body.*[].bettingId").type(JsonFieldType.NUMBER)
                                                        .description("베팅 ID"),
                                                fieldWithPath("body.*[].bettingName").type(JsonFieldType.STRING)
                                                        .description("베팅 이름")
                                        )
                                )
                                .responseSchema(Schema.schema("오픈 베팅 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 단일 조회_성공")
    public void betting_details_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/betting/{bettingId}", 10001L)
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
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.memberId").type(JsonFieldType.NUMBER)
                                                        .description("멤버 ID"),
                                                fieldWithPath("body.mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("대표 캐릭터 이미지"),
                                                fieldWithPath("body.memberNickname").type(JsonFieldType.STRING)
                                                        .description("멤버 닉네임"),
                                                fieldWithPath("body.bettingId").type(JsonFieldType.NUMBER)
                                                        .description("베팅 ID"),
                                                fieldWithPath("body.bettingName").type(JsonFieldType.STRING)
                                                        .description("베팅 이름")
                                        )
                                )
                                .responseSchema(Schema.schema("베팅 단일 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("베팅 삭제_성공")
    public void betting_remove_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                delete("/betting/{bettingId}", 10001L)
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
}
