package site.doto.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.item.dto.ItemBuyReq;
import site.doto.domain.item.entity.Item;
import site.doto.domain.item.entity.ItemPK;
import site.doto.domain.item.entity.ItemType;
import site.doto.domain.item.repository.ItemRepository;
import site.doto.domain.item.repository.ItemTypeRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.exception.CustomException;

import java.util.Optional;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemTypeRepository itemTypeRepository;

    public void buyItem(Long memberId, Long itemId, ItemBuyReq itemBuyReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        ItemType itemType = itemTypeRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));

        Integer coinUsage = itemBuyReq.getCount() * itemType.getPrice();
        if(member.getCoin() < coinUsage) {
            throw new CustomException(COIN_NOT_ENOUGH);
        }

        ItemPK itemPK = new ItemPK(memberId, itemId);
        Optional<Item> item = itemRepository.findById(itemPK);

        if(item.isPresent()) {
            Item existingItem = item.get();
            existingItem.modify(existingItem.getCount() + itemBuyReq.getCount());
        } else {
            itemRepository.save(itemBuyReq.toEntity(member, itemType, itemPK));
        }

        member.modify(member.getCoin() - coinUsage);
    }
}
