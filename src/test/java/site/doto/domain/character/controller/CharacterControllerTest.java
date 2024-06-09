package site.doto.domain.character.controller;
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
import site.doto.domain.character.dto.CharacterBuyReq;

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
public class CharacterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("나의 캐릭터 전체 조회 성공")
    public void character_list_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/members/characters")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CHARACTERS_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CHARACTERS_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "나의 캐릭터 전체 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Mypage API")
                                .summary("나의 캐릭터 전체 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.*[].id").type(JsonFieldType.NUMBER)
                                                        .description("Character Id"),
                                                fieldWithPath("body.*[].name").type(JsonFieldType.STRING)
                                                        .description("Character 이름"),
                                                fieldWithPath("body.*[].img").type(JsonFieldType.STRING)
                                                        .description("Character 이미지 url"),
                                                fieldWithPath("body.*[].level").type(JsonFieldType.NUMBER)
                                                        .description("Character 레벨"),
                                                fieldWithPath("body.*[].exp").type(JsonFieldType.NUMBER)
                                                        .description("Character 남은 경험치"),
                                                fieldWithPath("body.*[].description").type(JsonFieldType.STRING)
                                                        .description("Character 설명")
                                        )
                                )
                                .responseSchema(Schema.schema("나의 캐릭터 전체 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("대표 캐릭터 변경 성공")
    public void main_character_modify_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/characters/{characterId}", 2L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CHARACTER_MODIFY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CHARACTER_MODIFY_OK.getMessage()))
                .andDo(document(
                        "대표 캐릭터 변경",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Mypage API")
                                .summary("대표 캐릭터 변경 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("characterId").description("캐릭터 아이디")
                                )
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("나의 캐릭터 판매 성공")
    public void character_sell_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                delete("/members/characters/{characterId}", 2L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CHARACTER_SELL_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CHARACTER_SELL_OK.getMessage()))
                .andDo(document(
                        "나의 캐릭터 판매",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Mypage API")
                                .summary("나의 캐릭터 판매 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("characterId").description("캐릭터 아이디")
                                )
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("알 정보 조회 성공")
    public void character_details_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/store/characters")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CHARACTER_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CHARACTER_INQUIRY_OK.getMessage()))
                .andExpect(jsonPath("$.body.id").value(1L))
                .andExpect(jsonPath("$.body.name").value("알"))
                .andExpect(jsonPath("$.body.img").value("이미지"))
                .andExpect(jsonPath("$.body.price").value(200))
                .andDo(document(
                        "알 정보 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("알 정보 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.id").type(JsonFieldType.NUMBER)
                                                        .description("Character Id"),
                                                fieldWithPath("body.name").type(JsonFieldType.STRING)
                                                        .description("Character 이름"),
                                                fieldWithPath("body.img").type(JsonFieldType.STRING)
                                                        .description("Character 이미지 url"),
                                                fieldWithPath("body.price").type(JsonFieldType.NUMBER)
                                                        .description("Character 가격")
                                        )
                                )
                                .responseSchema(Schema.schema("알 정보 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("알 구매 성공")
    public void character_buy_success() throws Exception {
        //given
        CharacterBuyReq characterBuyReq = new CharacterBuyReq();
        characterBuyReq.setCount(3);

        String content = gson.toJson(characterBuyReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/store/characters")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CHARACTERS_BUY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CHARACTERS_BUY_OK.getMessage()))
                .andDo(document(
                        "알 구매",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("알 구매 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        fieldWithPath("count").type(JsonFieldType.NUMBER)
                                                .description("구매할 알 개수")
                                )
                                .requestSchema(Schema.schema("알 구매 Request"))
                                .build()
                        ))
                );
    }
}
