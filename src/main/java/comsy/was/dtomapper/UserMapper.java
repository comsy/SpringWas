package comsy.was.dtomapper;

import comsy.was.domain.User;
import comsy.was.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends GenericMapper<UserDto, User>{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
