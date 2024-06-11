package site.doto.domain.member.controller;

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
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.member.dto.*;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.doto.global.status_code.SuccessCode.MEMBERS_SEARCH_OK;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class MemberControllerTest {
    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("회원가입_성공")
    public void signup_success() throws Exception {
        //given
        MemberAddReq memberAddReq = new MemberAddReq();
        memberAddReq.setNickname("두투투두");
        memberAddReq.setEmail("doto@naver.com");
        memberAddReq.setAuthCode("550e8400-e29b-41d4-a716-446655440000");
        memberAddReq.setPassword("1234");

        String content = gson.toJson(memberAddReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "회원가입",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("회원가입 API")
                                .requestFields(
                                        List.of(
                                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("authCode").type(JsonFieldType.STRING).description("이메일 인증코드"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호")
                                        )
                                )
                                .requestSchema(Schema.schema("회원가입 Request")) //Dto 이름
                                .build())
                ));
    }

    @Test
    @DisplayName("이메일 인증번호 발송_성공")
    public void email_success() throws Exception {
        //given
        AuthCodeSendReq authCodeSendReq = new AuthCodeSendReq();
        authCodeSendReq.setEmail("doto@naver.com");

        String content = gson.toJson(authCodeSendReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/members/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "이메일 인증번호 발송",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("이메일 인증번호 발송 API")
                                .requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일")
                                )
                                .requestSchema(Schema.schema("이메일 인증번호 발송 Request"))
                                .build())
                ));
    }

    @Test
    @DisplayName("이메일 인증번호 확인_성공")
    public void email_check_success() throws Exception {
        //given
        AuthCodeVerifyReq authCodeVerifyReq = new AuthCodeVerifyReq();
        authCodeVerifyReq.setEmail("doto@naver.com");
        authCodeVerifyReq.setAuthCode("550e8400-e29b-41d4-a716-446655440000");

        String content = gson.toJson(authCodeVerifyReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/members/email/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "이메일 인증번호 확인",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("이메일 인증번호 확인 API")
                                .requestFields(
                                        List.of(
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("authCode").type(JsonFieldType.STRING).description("이메일 인증번호")
                                        )
                                )
                                .requestSchema(Schema.schema("이메일 인증번호 확인 Request"))
                                .build())
                ));
    }

    @Test
    @DisplayName("로그인_성공")
    public void login_success() throws Exception {
        //given
        LoginReq loginReq = new LoginReq();
        loginReq.setEmail("doto@naver.com");
        loginReq.setPassword("1234");

        String content = gson.toJson(loginReq);

        //when
        ResultActions actions = mockMvc.perform(
                post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document(
                        "로그인",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("로그인 API")
                                .requestFields(
                                        List.of(
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호")
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER).description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("성공 메시지"),
                                                fieldWithPath("body.token").type(JsonFieldType.STRING).description("회원 토큰")
                                        )
                                )
                                .requestSchema(Schema.schema("로그인 Request"))
                                .responseSchema(Schema.schema("로그인 Response"))
                                .build())
                ));
    }

    @Test
    @DisplayName("회원 정보 수정_성공")
    public void member_modify_success() throws Exception {
        //given
        MemberModifyReq memberModifyReq = new MemberModifyReq();
        memberModifyReq.setNickname("뚜두두투");
        memberModifyReq.setDescription("이건 설명이에욤 ㅋ");

        String content = gson.toJson(memberModifyReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/modify")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "회원 정보 수정",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("회원 정보 수정 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원 닉네임(Optional)").optional(),
                                                fieldWithPath("description").type(JsonFieldType.STRING).description("회원 한줄소개(Optional)").optional()
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER).description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING).description("성공 메시지"),
                                                fieldWithPath("body.nickname").type(JsonFieldType.STRING).description("회원 닉네임"),
                                                fieldWithPath("body.description").type(JsonFieldType.STRING).description("회원 한줄소개")
                                        )
                                )
                                .requestSchema(Schema.schema("회원 정보 수정 Request"))
                                .responseSchema(Schema.schema("회원 정보 수정 Response"))
                                .build())
                ));
    }

    @Test
    @DisplayName("비밀번호 변경_성공")
    public void password_reset_success() throws Exception {
        //given
        PasswordModifyReq passwordModifyReq = new PasswordModifyReq();
        passwordModifyReq.setCurrentPassword("1234");
        passwordModifyReq.setChangePassword("12345");

        String content = gson.toJson(passwordModifyReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/password/reset")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "비밀번호 변경",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("비밀번호 변경 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("currentPassword").type(JsonFieldType.STRING).description("현재 비밀번호"),
                                                fieldWithPath("changePassword").type(JsonFieldType.STRING).description("변경할 비밀번호")
                                        )
                                )
                                .requestSchema(Schema.schema("비밀번호 변경 Request"))
                                .build())
                ));
    }

    @Test
    @DisplayName("비밀번호 찾기_성공")
    public void password_find_success() throws Exception {
        //given
        PasswordFindReq passwordFindReq = new PasswordFindReq();
        passwordFindReq.setEmail("doto@naver.com");
        passwordFindReq.setAuthCode("550e8400-e29b-41d4-a716-446655440000");
        passwordFindReq.setChangePassword("12345");

        String content = gson.toJson(passwordFindReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/password/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "비밀번호 찾기",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("비밀번호 찾기 API")
                                .requestFields(
                                        List.of(
                                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                                fieldWithPath("authCode").type(JsonFieldType.STRING).description("이메일 인증번호"),
                                                fieldWithPath("changePassword").type(JsonFieldType.STRING).description("변경할 비밀번호")
                                        )
                                )
                                .requestSchema(Schema.schema("비밀번호 찾기 Request"))
                                .build())
                ));
    }

    @Test
    @DisplayName("회원 탈퇴_성공")
    public void member_withdraw_success() throws Exception {
        //given


        //when
        ResultActions actions = mockMvc.perform(
                delete("/members")
                        .header("Authorization", "Bearer " + jwtToken)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "회원 탈퇴",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("회원 탈퇴 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .build())
                ));

    }

    @Test
    @DisplayName("유저 검색_성공")
    public void members_search_success() throws Exception {
        // given
        MembersSearchReq membersSearchReq = new MembersSearchReq();
        membersSearchReq.setSearchWord("검색어");
        membersSearchReq.setLastMemberId(10000L);

        String content = gson.toJson(membersSearchReq);

        // when
        ResultActions actions = mockMvc.perform(
                get("/members/search")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBERS_SEARCH_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBERS_SEARCH_OK.getMessage()))
                .andDo(document(
                        "유저 검색",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Member API")
                                .summary("유저 검색 API")
                                .requestHeaders(
                                        HeaderDocumentation.headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        List.of(
                                                fieldWithPath("searchWord").type(JsonFieldType.STRING)
                                                        .description("검색어"),
                                                fieldWithPath("lastMemberId").type(JsonFieldType.NUMBER)
                                                        .description("마지막 유저 Id(Optional)").optional()
                                        )
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.searchResult.content").type(JsonFieldType.ARRAY)
                                                        .description("검색 결과"),
                                                fieldWithPath("body.searchResult.*[].memberId").type(JsonFieldType.NUMBER)
                                                        .description("유저 Id"),
                                                fieldWithPath("body.searchResult.*[].nickname").type(JsonFieldType.STRING)
                                                        .description("유저 닉네임"),
                                                fieldWithPath("body.searchResult.*[].mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("유저 대표 캐릭터 이미지"),
                                                fieldWithPath("body.searchResult.*[].status").type(JsonFieldType.STRING)
                                                        .description("유저 관계 상태 코드"),
                                                fieldWithPath("body.searchResult.sliceNumber").type(JsonFieldType.NUMBER)
                                                        .description("슬라이스 번호"),
                                                fieldWithPath("body.searchResult.size").type(JsonFieldType.NUMBER)
                                                        .description("슬라이스 크기"),
                                                fieldWithPath("body.searchResult.hasNext").type(JsonFieldType.BOOLEAN)
                                                        .description("다음 슬라이스 여부"),
                                                fieldWithPath("body.searchResult.numberOfElements").type(JsonFieldType.NUMBER)
                                                        .description("요소의 수")
                                        )
                                )
                                .requestSchema(Schema.schema("유저 검색 Request"))
                                .responseSchema(Schema.schema("유저 검색 Response"))
                                .build()
                        )
                ));

    }
}