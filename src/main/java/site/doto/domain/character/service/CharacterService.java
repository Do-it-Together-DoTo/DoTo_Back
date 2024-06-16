package site.doto.domain.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.doto.domain.character.dto.CharacterDetailsRes;

import static site.doto.domain.character.enums.Egg.EGG;

@Service
@Transactional
@RequiredArgsConstructor
public class CharacterService {
    @Transactional(readOnly = true)
    public CharacterDetailsRes findEgg() {
        return new CharacterDetailsRes(0L, EGG.getName(), EGG.getImg(), EGG.getCost());
    }
}
