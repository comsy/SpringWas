package comsy.was.service;

import comsy.was.api.GenericApi;
import comsy.was.configuration.FluentdConfiguration;
import comsy.was.data.dao.CharacterDaoService;
import comsy.was.data.domain.Character;
import comsy.was.data.dto.CharacterDto;
import comsy.was.data.redis.entity.CharacterInfo;
import comsy.was.data.redis.repository.CharacterInfoRepository;
import comsy.was.exception.BusinessException;
import comsy.was.exception.ErrorCode;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.komamitsu.fluency.Fluency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CharacterService {

    private final CharacterDaoService characterDaoService;
    private final CharacterInfoRepository characterInfoRepository;
    private final Fluency fluency;

    public CharacterDto findOne(Long guid, Long id) throws IOException {

        Map<String, Object> event = new HashMap<>();
        event.put("name", "komamitsu");
        event.put("age", 42);
        event.put("rate", 3.14);
        fluency.emit(FluentdConfiguration.tag, event);

        Optional<CharacterDto> optCharacter = characterDaoService.getDto(guid, id);
        CharacterDto characterDto = optCharacter.orElseThrow(()->new BusinessException(ErrorCode.CHARACTER_NOT_EXIST, "캐릭터가 없어요."));
        Character character = characterDto.toEntity();

        characterDaoService.deleteCache(guid);

        Optional<CharacterDto> optCharacter2 = characterDaoService.getDto(guid, id);

        return optCharacter2.orElse(null);
    }


    public List<CharacterDto> findAll(Long guid){
        return characterDaoService.getDtoList(guid);
    }

    @Transactional  // write 가 있는 경우
    public void addCharacter(Long guid){

        Character newCharacter = Character.builder()
                .guid(guid)
                .category(0)
                .characterId(0L)
                .build();

        Long id = characterDaoService.saveEntity(newCharacter);


    }


    @Transactional  // write 가 있는 경우
    public List<CharacterDto> characterTest(Long guid){
        characterDaoService.getDtoList(guid);

        Character newCharacter = Character.builder()
                .guid(guid)
                .category(0)
                .characterId(0L)
                .build();

        Long id = characterDaoService.saveEntity(newCharacter);

        return characterDaoService.getDtoList(guid);
    }

    @Transactional  // write 가 있는 경우
    public ResultAddCharacterExp addCharacterExp(Long guid, Long id, int addExp){
        Optional<CharacterDto> optCharacter = characterDaoService.getDto(guid, id);
        CharacterDto characterDto = optCharacter.orElseThrow(()->new BusinessException(ErrorCode.CHARACTER_NOT_EXIST, "캐릭터가 없어요."));
        Character character = characterDto.toEntity();

        // DDD
        boolean isLevelUp = character.addExpAndLevelUp(addExp);

        characterDaoService.saveEntity(character);

        return ResultAddCharacterExp.builder()
                .characterList(characterDaoService.getDtoList(guid))
                .isLevelUp(isLevelUp).build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class ResultAddCharacterExp {
        private List<CharacterDto> characterList;
        private Boolean isLevelUp;
    }

    @Transactional  // write 가 있는 경우
    public CharacterInfo testCharacterRedis(Long guid, Long id){
        Optional<CharacterDto> optCharacter = characterDaoService.getDto(guid, id);
        CharacterDto characterDto = optCharacter.orElseThrow(()->new BusinessException(ErrorCode.CHARACTER_NOT_EXIST, "캐릭터가 없어요."));
        Character character = characterDto.toEntity();

        Optional<CharacterInfo> optCharacterInfo = characterInfoRepository.findById(character.getId());

        CharacterInfo characterInfo;
        if( optCharacterInfo.isEmpty() ){
            characterInfo = CharacterInfo.builder()
                    .id(character.getId())
                    .name("캐릭터"+character.getId())
                    .build();
        }
        else{
            characterInfo = optCharacterInfo.get();

            characterInfo.changeName("변경캐릭터"+character.getId());
        }

        characterInfoRepository.save(characterInfo);

        return characterInfo;
    }
}
