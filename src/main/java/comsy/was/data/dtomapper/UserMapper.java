package comsy.was.data.dtomapper;

import comsy.was.data.domain.User;
import comsy.was.data.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends GenericMapper<UserDto, User>{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
