package comsy.was.data.redis.repository;

import comsy.was.data.redis.entity.CharacterInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterInfoRepository extends CrudRepository<CharacterInfo, Long> {

    CharacterInfo findByName(String name);
}