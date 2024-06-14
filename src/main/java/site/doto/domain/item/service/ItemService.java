package site.doto.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.item.dto.ItemBuyReq;
import site.doto.domain.item.entity.ItemPK;
import site.doto.domain.item.entity.ItemType;
import site.doto.domain.item.repository.ItemRepository;
import site.doto.domain.item.repository.ItemTypeRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.exception.CustomException;

import static site.doto.global.status_code.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemTypeRepository itemTypeRepository;

    public void buyItem(Long memberId, Long itemTypeId, ItemBuyReq itemBuyReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        ItemType itemType = itemTypeRepository.findById(itemTypeId)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));

        Integer coinUsage = itemBuyReq.getCount() * itemType.getPrice();
        if(member.getCoin() < coinUsage) {
            throw new CustomException(COIN_NOT_ENOUGH);
        }

        ItemPK itemPK = new ItemPK(memberId, itemTypeId);

        try {
            int updatedCount = itemRepository.updateItemCount(memberId, itemTypeId, itemBuyReq.getCount());
            if(updatedCount == 0) {
                itemRepository.save(itemBuyReq.toEntity(member, itemType, itemPK));
            }
        } catch(DataIntegrityViolationException e) {
            throw new CustomException(BAD_REQUEST);
        }

        try {
            memberRepository.updateCoin(memberId, -coinUsage);
        } catch(DataIntegrityViolationException e) {
            throw new CustomException(COIN_NOT_ENOUGH);
        }
    }
}
