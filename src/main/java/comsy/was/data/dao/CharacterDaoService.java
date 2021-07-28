package comsy.was.data.dao;

import comsy.was.data.domain.Character;
import comsy.was.data.dto.CharacterDto;
import comsy.was.data.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterDaoService {

    // self-autowired
    @Resource
    private CharacterDaoService self;

    private final CharacterRepository characterRepository;


    //== CQRS : QUERY ==//
    @Cacheable(value="characterList", key = "#guid", cacheManager = "redisCacheManager7Day")
    public List<CharacterDto> getDtoList(Long guid){
        List<Character> dtoList = characterRepository.findByGuid(guid);

        return CharacterDto.from(dtoList);
    }

    public Optional<CharacterDto> getDto(Long guid, Long id){
        // 같은 클래스 내의 메소드 호출 시 Cacheable 자동 주입.
        List<CharacterDto> dtoList = self.getDtoList(guid);

        Optional<CharacterDto> optionalDto = dtoList.stream()
                .filter(dto -> dto.getId().equals(id))
                .findFirst();

        // 실수 방지를 위해 Optional로  return
        return optionalDto;
    }

    //== CQRS : COMMAND ==//
    public Long saveEntity(Character entity){
        characterRepository.save(entity);

        return entity.getId();  // [CQRS위반] 성능을위해 id는 반환
    }

    public void deleteEntity(Character entity){
        characterRepository.delete(entity);
    }


    //== Cache Only ==//
    @CacheEvict(value="characterList", key = "#guid", cacheManager = "redisCacheManager7Day")
    public void deleteCache(Long guid){
    }
}
