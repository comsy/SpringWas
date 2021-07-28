package comsy.was.controller;

import comsy.was.api.CharacterAddExpApi;
import comsy.was.api.CharacterFindApi;
import comsy.was.api.CharacterFindOneApi;
import comsy.was.data.dto.CharacterDto;
import comsy.was.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/character/findList")
    public CharacterFindApi.Response findCharacterList(@RequestBody @Valid CharacterFindApi.Request request){

        Long guid = request.getGuid();
        log.debug("guid : " + guid);

        List<CharacterDto> characterDtoList = characterService.findAll(guid);

        return new CharacterFindApi.Response(200, "", characterDtoList);
    }

    @PostMapping("/character/findOne")
    public CharacterFindOneApi.Response findCharacterOne(@RequestBody @Valid CharacterFindOneApi.Request request) throws IOException {

        Long guid = request.getGuid();
        Long id = request.getId();
        log.debug("guid : " + guid);
        log.debug("id : " + id);

        CharacterDto characterDto = characterService.findOne(guid, id);

        return new CharacterFindOneApi.Response(200, "", characterDto);
    }

    @PostMapping("/character/cacheTest")
    public CharacterFindApi.Response cacheTest(@RequestBody @Valid CharacterFindApi.Request request){

        Long guid = request.getGuid();
        log.debug("guid : " + guid);

        List<CharacterDto> characterDtoList = characterService.characterTest(guid);

        return new CharacterFindApi.Response(200, "", characterDtoList);
    }

    @PostMapping("/character/addExp")
    public CharacterAddExpApi.Response cacheTest(@RequestBody @Valid CharacterAddExpApi.Request request){

        Long guid = request.getGuid();
        Long id = request.getId();
        int addExp = request.getAddExp();
        log.debug("guid : " + guid);
        log.debug("id : " + id);
        log.debug("addExp : " + addExp);

        CharacterService.ResultAddCharacterExp resultAddCharacterExp = characterService.addCharacterExp(guid, id, addExp);

        return new CharacterAddExpApi.Response(200, "", resultAddCharacterExp.getCharacterList(), resultAddCharacterExp.getIsLevelUp());
    }
}
