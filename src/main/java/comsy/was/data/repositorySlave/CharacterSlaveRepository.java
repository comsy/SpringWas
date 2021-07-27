package comsy.was.data.repositorySlave;

import comsy.was.data.domain.Character;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterSlaveRepository extends JpaRepository<Character, Long> {

    List<Character> findByGuid(Long guid);

    @CacheEvict(value="characterSlaveList", key = "#entity.guid", cacheManager = "redisCacheManager")
    @Override
    <S extends Character> S save(S entity);

    @CacheEvict(value="characterSlaveList", key = "#entity.guid", cacheManager = "redisCacheManager")
    @Override
    void delete(Character entity);


}
