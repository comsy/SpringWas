package comsy.was.data.repository;

import comsy.was.data.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByGuid(Long guid);

    @Override
    @CacheEvict(value="user", key = "#entity.guid")
    <S extends User> S save(S entity);

    @Override
    @CacheEvict(value="user", key = "#entity.guid")
    void delete(User entity);
}
