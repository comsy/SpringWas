package comsy.was.controller;

import comsy.was.data.dao.UserDaoService;
import comsy.was.data.domain.User;
import comsy.was.api.UserFindApi;
import comsy.was.data.dto.UserDto;
import comsy.was.exception.BusinessException;
import comsy.was.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserDaoService userDaoService;

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
}
