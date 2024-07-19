package site.doto.domain.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.item.dto.*;
import site.doto.domain.item.service.ItemService;
import site.doto.global.dto.ResponseDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RequiredArgsConstructor
@RestController
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/members/items")
    public ResponseDto<ItemListRes> itemList() {
        Long memberId = 1L;

        ItemListRes result = itemService.findItems(memberId);

        return ResponseDto.success(ITEMS_INQUIRY_OK, result);
    }

    @PatchMapping("/members/items")
    public ResponseDto<?> itemUse(
            @RequestBody @Valid ItemUseReq itemUseReq) {
        return ResponseDto.success(ITEM_USE_OK, null);
    }

    @PatchMapping("/members/items/{itemId}")
    public ResponseDto<?> itemSell(
            @PathVariable("itemId") Long itemId,
            @RequestBody @Valid ItemSellReq itemSellReq) {
        return ResponseDto.success(ITEMS_SELL_OK, null);
    }

    @GetMapping("/store/items")
    public ResponseDto<StoreItemListRes> storeItemList() {
        StoreItemListRes result = itemService.findStoreItems();

        return ResponseDto.success(STORE_INQUIRY_OK, result);
    }

    @GetMapping("/store/items/{itemId}")
    public ResponseDto<StoreItemDetailsRes> storeItemDetails(
            @PathVariable("itemId") Long itemId) {
        StoreItemDetailsRes result = itemService.findStoreItem(itemId);

        return ResponseDto.success(ITEM_INQUIRY_OK, result);
    }

    @PutMapping("/store/items/{itemId}")
    public ResponseDto<?> itemBuy(
            @PathVariable("itemId") Long itemId,
            @RequestBody @Valid ItemBuyReq itemBuyReq) {
        Long memberId = 1L;

        itemService.buyItem(memberId, itemId, itemBuyReq);

        return ResponseDto.success(ITEMS_BUY_OK, null);
    }

}
