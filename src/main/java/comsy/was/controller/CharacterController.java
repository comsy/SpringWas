package comsy.was.controller;

import comsy.was.api.CharacterAddExpApi;
import comsy.was.api.CharacterFindApi;
import comsy.was.api.CharacterFindOneApi;
import comsy.was.api.CharacterRedisTestApi;
import comsy.was.data.dto.CharacterDto;
import comsy.was.data.redis.entity.CharacterInfo;
import comsy.was.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CharacterController {

    private final CharacterService characterService;
    private final RedisTemplate<String, Object> redisDataTemplate;

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

    @PostMapping("/character/redisTest")
    public CharacterRedisTestApi.Response cacheTest(@RequestBody @Valid CharacterRedisTestApi.Request request){

        Long guid = request.getGuid();
        Long id = request.getId();
        log.debug("guid : " + guid);
        log.debug("id : " + id);

        CharacterInfo characterInfo = characterService.testCharacterRedis(guid, id);


        // TEST - String
        ValueOperations<String, Object> stringObjectValueOperations = redisDataTemplate.opsForValue();
        stringObjectValueOperations.set("comsyWas.string", "test");
        Object comsyWas = stringObjectValueOperations.get("comsyWas");
        log.debug("comsyWas.string : " + comsyWas);

        // TEST - String
        ZSetOperations<String, Object> stringObjectZSetOperations = redisDataTemplate.opsForZSet();
        stringObjectZSetOperations.add("comsyWas.zset", "test1", 9);
        Long rank = stringObjectZSetOperations.rank("comsyWas.zset", "test");
        log.debug("comsyWas.zset rank : " + rank);

        return new CharacterRedisTestApi.Response(200, "", characterInfo);
    }
}
