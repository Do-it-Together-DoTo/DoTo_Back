package site.doto.domain.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.character.dto.CharacterBuyReq;
import site.doto.domain.character.dto.CharacterDetailsRes;
import site.doto.domain.character.entity.Character;
import site.doto.domain.character.entity.CharacterType;
import site.doto.domain.character.repository.CharacterRepository;
import site.doto.domain.character.repository.CharacterTypeRepository;
import site.doto.domain.member.entity.Member;
import site.doto.domain.member.repository.MemberRepository;
import site.doto.global.exception.CustomException;
import site.doto.global.redis.RedisUtils;

import java.time.LocalDate;
import java.util.Random;

import static site.doto.domain.character.enums.Egg.EGG;
import static site.doto.global.status_code.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterService {
    private final Integer CharacterTypeCount = 3;

    private final MemberRepository memberRepository;
    private final CharacterTypeRepository characterTypeRepository;
    private final CharacterRepository characterRepository;
    private final RedisUtils redisUtils;

    @Transactional(readOnly = true)
    public CharacterDetailsRes findEgg() {
        return new CharacterDetailsRes(0L, EGG.getName(), EGG.getImg(), EGG.getCost());
    }

    public void buyCharacter(Long memberId, CharacterBuyReq characterBuyReq) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Integer coinUsage = characterBuyReq.getCount() * EGG.getCost();
        if (member.getCoin() < coinUsage) {
            throw new CustomException(COIN_NOT_ENOUGH);
        }

        for(int i = 0; i < characterBuyReq.getCount(); i++) {
            Long characterTypeId = getRandomCharacterTypeId();

            CharacterType characterType = characterTypeRepository.findById(characterTypeId)
                    .orElseThrow(() -> new CustomException(CHARACTER_TYPE_NOT_FOUND));

            characterRepository.save(characterBuyReq.toEntity(member, characterType));

            try {
                memberRepository.updateCoin(memberId, -coinUsage);
            } catch (DataIntegrityViolationException e) {
                throw new CustomException(COIN_NOT_ENOUGH);
            }

            LocalDate currentDate = LocalDate.now();
            redisUtils.updateRecordToRedis(memberId, currentDate.getYear(), currentDate.getMonthValue(), "coinUsage", coinUsage);
        }
    }

    private Long getRandomCharacterTypeId() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return (long) (random.nextInt(CharacterTypeCount) + 1);
    }
}
