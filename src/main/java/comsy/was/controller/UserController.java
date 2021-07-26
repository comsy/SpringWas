package comsy.was.controller;

import comsy.was.api.CharacterFindApi;
import comsy.was.api.CharacterFindOneApi;
import comsy.was.data.dao.UserDaoService;
import comsy.was.data.domain.User;
import comsy.was.api.UserFindApi;
import comsy.was.data.dto.CharacterDto;
import comsy.was.data.dto.UserDto;
import comsy.was.exception.BusinessException;
import comsy.was.exception.ErrorCode;
import comsy.was.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserDaoService userDaoService;

    private final CharacterService characterService;

    @GetMapping("/user/{Id}")
    public ResponseEntity<UserFindApi.Response> getUser(@PathVariable("Id") Long guid){
        log.debug("guid : " + guid);

        Optional<UserDto> optionalUserDto = userDaoService.getDto(guid);
        UserDto userDto = optionalUserDto.orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_EXIST, "유저 없어요."));

        return ResponseEntity.ok(new UserFindApi.Response(200, "", userDto));
    }

    @PostMapping("/user/findOne")
    public UserFindApi.Response findUser(@RequestBody @Valid UserFindApi.Request request){

        Long guid = request.getGuid();
        log.debug("guid : " + guid);

        Optional<UserDto> optionalUserDto = userDaoService.getDto(guid);
        UserDto userDto = optionalUserDto.orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_EXIST, "유저 없어요."));

        return new UserFindApi.Response(200, "", userDto);
    }

    @PostMapping("/user/findOne2")
    public UserFindApi.Response findUser(@RequestBody Map<String, String> request){

        Long guid = Long.parseLong(request.get("guid"));
        log.debug("guid : " + guid);

        Optional<UserDto> optionalUserDto = userDaoService.getDto(guid);
        UserDto userDto = optionalUserDto.orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_EXIST, "유저 없어요."));
        User user = userDto.toEntity();

        UserDto userDto2 = UserDto.from(user);

        return new UserFindApi.Response(200, "", userDto2);
    }

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
}
