package site.doto.domain.record.controller;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
import static site.doto.global.status_code.SuccessCode.RECORDS_INQUIRY_OK;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class RecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("Record 조회 성공")
    public void record_details_success() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("year", "2024");
        params.add("month", "4");

        // when
        ResultActions actions = mockMvc.perform(
             get("/records")
                     .header("Authorization", jwtToken)
                     .accept(MediaType.APPLICATION_JSON)
                     .contentType(MediaType.APPLICATION_JSON)
                     .params(params)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(RECORDS_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(RECORDS_INQUIRY_OK.getMessage()))
                .andExpect(jsonPath("$.body.year").value(2024))
                .andExpect(jsonPath("$.body.month").value(4))
                .andExpect(jsonPath("$.body.coinUsage").value(2000))
                .andExpect(jsonPath("$.body.coinEarned").value(200))
                .andExpect(jsonPath("$.body.betAmount").value(10))
                .andExpect(jsonPath("$.body.betParticipation").value(3))
                .andExpect(jsonPath("$.body.betWins").value(2))
                .andExpect(jsonPath("$.body.betProfit").value(100))
                .andExpect(jsonPath("$.body.myBetOpen").value(3))
                .andDo(document(
                        "Record 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Record API")
                                .summary("Record 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestParameters(
                                        parameterWithName("year").description("연도"),
                                        parameterWithName("month").description("월")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.year").type(JsonFieldType.NUMBER)
                                                        .description("연도"),
                                                fieldWithPath("body.month").type(JsonFieldType.NUMBER)
                                                        .description("월"),
                                                fieldWithPath("body.coinUsage").type(JsonFieldType.NUMBER)
                                                        .description("코인 사용량"),
                                                fieldWithPath("body.coinEarned").type(JsonFieldType.NUMBER)
                                                        .description("코인 획득량"),
                                                fieldWithPath("body.betAmount").type(JsonFieldType.NUMBER)
                                                        .description("베팅을 건 코인의 개수"),
                                                fieldWithPath("body.betParticipation").type(JsonFieldType.NUMBER)
                                                        .description("베팅 참여 횟수"),
                                                fieldWithPath("body.betWins").type(JsonFieldType.NUMBER)
                                                        .description("베팅 이긴 횟수"),
                                                fieldWithPath("body.betProfit").type(JsonFieldType.NUMBER)
                                                        .description("베팅 수익"),
                                                fieldWithPath("body.myBetOpen").type(JsonFieldType.NUMBER)
                                                        .description("내가 연 베팅 횟수"),
                                                fieldWithPath("body.*[].name").type(JsonFieldType.STRING)
                                                        .description("카테고리 이름"),
                                                fieldWithPath("body.*[].total").type(JsonFieldType.NUMBER)
                                                        .description("투두 등록 개수"),
                                                fieldWithPath("body.*[].achieved").type(JsonFieldType.NUMBER)
                                                        .description("투두 달성 개수")
                                        )
                                )
                                .requestSchema(Schema.schema("Record 조회 Request"))
                                .responseSchema(Schema.schema("Record 조회 Response"))
                                .build()
                        ))
                );

    }
}
