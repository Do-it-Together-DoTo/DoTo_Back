package site.doto.domain.character.controller;

import org.springframework.web.bind.annotation.*;
import site.doto.domain.character.dto.CharacterBuyReq;
import site.doto.domain.character.dto.CharacterDetailsRes;
import site.doto.domain.character.dto.CharacterDto;
import site.doto.domain.character.dto.CharacterListRes;
import site.doto.global.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
public class CharacterController {
    @GetMapping("/members/characters")
    public ResponseDto<?> characterList() {
        List<CharacterDto> characters = new ArrayList<>();

        for(int i = 1; i <= 10; i++) {
            characters.add(CharacterDto.builder()
                    .id(10000L + i)
                    .name("캐릭터이름")
                    .level(10+i)
                    .img("이미지")
                    .description("설명")
                    .build());
        }

        CharacterListRes result = new CharacterListRes();
        result.setCharacters(characters);

        return ResponseDto.success(CHARACTERS_INQUIRY_OK, result);
    }

    @PatchMapping("/members/characters/{characterId}")
    public ResponseDto<?> mainCharacterModify(
            @PathVariable("characterId") Long characterId) {
        return ResponseDto.success(CHARACTER_MODIFY_OK, null);
    }

    @DeleteMapping("/members/characters/{characterId}")
    public ResponseDto<?> characterSell(
            @PathVariable("characterId") Long characterId) {
        return ResponseDto.success(CHARACTER_SELL_OK, null);
    }

    @GetMapping("/store/characters")
    public ResponseDto<?> characterDetails() {
        CharacterDetailsRes characterDetailsRes = new CharacterDetailsRes(
                1L, "알", "이미지", 200);

        return ResponseDto.success(CHARACTER_INQUIRY_OK, characterDetailsRes);
    }

    @PostMapping("/store/characters")
    public ResponseDto<?> characterBuy(
            @RequestBody CharacterBuyReq characterBuyReq) {
        return ResponseDto.success(CHARACTERS_BUY_OK, null);
    }

}
