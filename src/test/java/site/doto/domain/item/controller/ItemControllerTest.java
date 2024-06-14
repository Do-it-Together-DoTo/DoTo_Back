package site.doto.domain.item.controller;

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
import site.doto.domain.item.dto.ItemBuyReq;
import site.doto.domain.item.dto.ItemSellReq;
import site.doto.domain.item.dto.ItemUseReq;

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
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("나의 아이템 전체 조회 성공")
    public void item_list_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/members/items")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ITEMS_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ITEMS_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "나의 아이템 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Mypage API")
                                .summary("나의 아이템 전체 조회 API")
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
                                                        .description("Item Id"),
                                                fieldWithPath("body.*[].name").type(JsonFieldType.STRING)
                                                        .description("Item 이름"),
                                                fieldWithPath("body.*[].img").type(JsonFieldType.STRING)
                                                        .description("Item 이미지 url"),
                                                fieldWithPath("body.*[].count").type(JsonFieldType.NUMBER)
                                                        .description("Item 개수"),
                                                fieldWithPath("body.*[].grade").type(JsonFieldType.STRING)
                                                        .description("Item 등급")
                                        )
                                )
                                .responseSchema(Schema.schema("나의 아이템 전체 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("나의 아이템 사용 성공")
    public void item_use_success() throws Exception {
        //given
        ItemUseReq itemUseReq = new ItemUseReq();
        itemUseReq.setItemTypeId(1L);
        itemUseReq.setCharacterId(2L);
        itemUseReq.setCount(3);

        String content = gson.toJson(itemUseReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/items")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ITEM_USE_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ITEM_USE_OK.getMessage()))
                .andDo(document(
                        "나의 아이템 사용",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Mypage API")
                                .summary("나의 아이템 사용 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .requestFields(
                                        fieldWithPath("characterId").type(JsonFieldType.NUMBER)
                                                .description("Character Id"),
                                        fieldWithPath("itemTypeId").type(JsonFieldType.NUMBER)
                                                .description("ItemType Id"),
                                        fieldWithPath("count").type(JsonFieldType.NUMBER)
                                                .description("Item 사용 개수")
                                )
                                .requestSchema(Schema.schema("나의 아이템 사용 Request"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("나의 아이템 판매 성공")
    public void item_sell_success() throws Exception {
        //given
        ItemSellReq itemSellReq = new ItemSellReq();
        itemSellReq.setCount(5);

        String content = gson.toJson(itemSellReq);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/items/{itemId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ITEMS_SELL_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ITEMS_SELL_OK.getMessage()))
                .andDo(document(
                        "나의 아이템 판매",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Mypage API")
                                .summary("나의 아이템 판매 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("itemId").description("아이템 아이디")
                                )
                                .requestFields(
                                        fieldWithPath("count").type(JsonFieldType.NUMBER)
                                                .description("판매할 아이템 개수")
                                )
                                .requestSchema(Schema.schema("나의 아이템 판매 Request"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("아이템 목록 전체 조회 성공")
    public void store_item_list_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/store/items")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(STORE_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(STORE_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "아이템 목록 전체 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("아이템 목록 전체 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body.*[].id").type(JsonFieldType.NUMBER)
                                                .description("ItemType 아이디"),
                                        fieldWithPath("body.*[].name").type(JsonFieldType.STRING)
                                                .description("ItemType 이름"),
                                        fieldWithPath("body.*[].img").type(JsonFieldType.STRING)
                                                .description("ItemType 이미지 Url"),
                                        fieldWithPath("body.*[].price").type(JsonFieldType.NUMBER)
                                                .description("ItemType 가격"),
                                        fieldWithPath("body.*[].grade").type(JsonFieldType.STRING)
                                                .description("ItemType 등급")
                                )
                                .responseSchema(Schema.schema("아이템 목록 전체 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("아이템 개별 조회 성공")
    public void store_item_details_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/store/items/{itemId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ITEM_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ITEM_INQUIRY_OK.getMessage()))
                .andExpect(jsonPath("$.body.img").value("이미지 url"))
                .andExpect(jsonPath("$.body.name").value("아이템 이름"))
                .andExpect(jsonPath("$.body.description").value("아이템 설명"))
                .andDo(document(
                        "아이템 개별 조회 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("아이템 개별 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("itemId").description("아이템 아이디")
                                )
                                .responseFields(
                                        fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                .description("성공 코드"),
                                        fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                .description("성공 메시지"),
                                        fieldWithPath("body.name").type(JsonFieldType.STRING)
                                                .description("ItemType 이름"),
                                        fieldWithPath("body.img").type(JsonFieldType.STRING)
                                                .description("ItemType 이미지 Url"),
                                        fieldWithPath("body.description").type(JsonFieldType.STRING)
                                                .description("ItemType 설명")
                                )
                                .responseSchema(Schema.schema("아이템 개별 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("아이템 구매 성공")
    public void item_buy_success() throws Exception {
        //given
        ItemBuyReq itemBuyReq = new ItemBuyReq();
        itemBuyReq.setCount(3);

        String content = gson.toJson(itemBuyReq);

        //when
        ResultActions actions = mockMvc.perform(
                put("/store/items/{itemId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ITEMS_BUY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ITEMS_BUY_OK.getMessage()))
                .andDo(document(
                        "아이템 구매",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Store API")
                                .summary("아이템 구매 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("itemId").description("아이템 아이디")
                                )
                                .requestFields(
                                        fieldWithPath("count").type(JsonFieldType.NUMBER)
                                                .description("구매할 아이템 개수")
                                )
                                .requestSchema(Schema.schema("아이템 구매 Request"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("아이템 구매 - 검증 실패")
    public void item_buy_validation_fail() throws Exception {
        // given
        ItemBuyReq itemBuyReq1 = new ItemBuyReq();
        String content1 = gson.toJson(itemBuyReq1);

        ItemBuyReq itemBuyReq2 = new ItemBuyReq();
        itemBuyReq2.setCount(0);
        String content2 = gson.toJson(itemBuyReq2);

        // when
        ResultActions actions1 = mockMvc.perform(
                put("/store/items/{itemId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content1)
        );

        ResultActions actions2 = mockMvc.perform(
                put("/store/items/{itemId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content2)
        );

        // then
        actions1
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));

        actions2
                .andExpect(jsonPath("$.header.httpStatusCode").value(BIND_EXCEPTION.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(BIND_EXCEPTION.getMessage()));
    }

    @Test
    @DisplayName("아이템 구매 - 존재하지 않는 아이템")
    public void item_buy_item_not_found() throws Exception {
        // given
        ItemBuyReq itemBuyReq = new ItemBuyReq();
        itemBuyReq.setCount(3);

        String content = gson.toJson(itemBuyReq);

        // when
        ResultActions actions = mockMvc.perform(
                put("/store/items/{itemId}", 10000L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(ITEM_NOT_FOUND.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(ITEM_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("아이템 구매 - 코인 부족")
    public void item_buy_coin_not_enough() throws Exception {
        // given
        ItemBuyReq itemBuyReq = new ItemBuyReq();
        itemBuyReq.setCount(1000);

        String content = gson.toJson(itemBuyReq);

        // when
        ResultActions actions = mockMvc.perform(
                put("/store/items/{itemId}", 1L)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(COIN_NOT_ENOUGH.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(COIN_NOT_ENOUGH.getMessage()));
    }
}
