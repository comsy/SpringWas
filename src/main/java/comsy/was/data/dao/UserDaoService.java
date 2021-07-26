package comsy.was.data.dao;

import comsy.was.data.domain.User;
import comsy.was.data.dto.UserDto;
import comsy.was.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDaoService {

    // self-autowired
    @Resource
    private UserDaoService self;

    private final UserRepository userRepository;

    //== CQRS : QUERY ==//
    @Cacheable(value="user", key = "#guid")
    public List<UserDto> getDtoList(Long guid){
        List<User> dtoList = userRepository.findByGuid(guid);

        return UserDto.from(dtoList);
    }

    public Optional<UserDto> getDto(Long guid){
        // 같은 클래스 내의 메소드 호출 시 Cacheable 자동 주입.
        List<UserDto> dtoList = self.getDtoList(guid);

        Optional<UserDto> optionalDto = dtoList.stream()
                .filter(dto -> dto.getGuid().equals(guid))
                .findFirst();

        // 실수 방지를 위해 Optional로  return
        return optionalDto;
    }

    //== CQRS : COMMAND ==//
    public void saveEntity(User entity){
        userRepository.save(entity);
    }

    public void deleteEntity(User entity){
        userRepository.delete(entity);
    }


    //== Cache Only ==//
    @CacheEvict(value="characterList", key = "#guid")
    public void deleteCache(Long guid){
    }
}
