package site.doto.domain.item.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.item.dto.*;
import site.doto.domain.item_type.dto.ItemTypeDto;
import site.doto.domain.item_type.dto.StoreItemListRes;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
public class ItemController {
    @GetMapping("/members/items")
    public ResponseDto<ItemListRes> itemList() {
        List<ItemDto> items = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            items.add(ItemDto.builder()
                    .id(10L+i)
                    .name("아이템 이름")
                    .img("이미지 url")
                    .count(10+i)
                    .grade("NORMAL")
                    .build());
        }

        ItemListRes result = new ItemListRes();
        result.setItems(items);

        return ResponseDto.success(ITEMS_INQUIRY_OK, result);
    }

    @PatchMapping("/members/items")
    public ResponseDto<?> itemUse(
            @RequestBody ItemUseReq itemUseReq) {
        return ResponseDto.success(ITEM_USE_OK, null);
    }

    @PatchMapping("/members/items/{itemId}")
    public ResponseDto<?> itemSell(
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemSellReq itemSellReq) {
        return ResponseDto.success(ITEMS_SELL_OK, null);
    }

    @GetMapping("/store/items")
    public ResponseDto<StoreItemListRes> storeItemList() {
        List<ItemTypeDto> items = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            items.add(ItemTypeDto.builder()
                    .id(10L+i)
                    .name("아이템 이름")
                    .img("이미지 url")
                    .price(100+i)
                    .grade("NORMAL")
                    .build());
        }

        StoreItemListRes result = new StoreItemListRes();
        result.setItems(items);

        return ResponseDto.success(STORE_INQUIRY_OK, result);
    }

    @GetMapping("/store/items/{itemId}")
    public ResponseDto<StoreItemDetailsRes> storeItemDetails(
            @PathVariable("itemId") Long itemId) {
        StoreItemDetailsRes result = new StoreItemDetailsRes(300, "아이템 설명");

        return ResponseDto.success(ITEM_INQUIRY_OK, result);
    }

    @PutMapping("/store/items/{itemId}")
    public ResponseDto<?> itemBuy(
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemBuyReq itemBuyReq) {
        return ResponseDto.success(ITEMS_BUY_OK, null);
    }

}
