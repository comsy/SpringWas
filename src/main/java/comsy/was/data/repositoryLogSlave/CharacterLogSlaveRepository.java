package comsy.was.data.repositoryLogSlave;

import comsy.was.data.domain.log.CharacterLog;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterLogSlaveRepository extends JpaRepository<CharacterLog, Long> {

    List<CharacterLog> findByGuid(Long guid);

    @CacheEvict(value="characterList", key = "#entity.guid")
    @Override
    <S extends CharacterLog> S save(S entity);

    @CacheEvict(value="characterList", key = "#entity.guid")
    @Override
    void delete(CharacterLog entity);


}
