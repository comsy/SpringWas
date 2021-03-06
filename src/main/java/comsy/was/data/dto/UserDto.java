package comsy.was.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import comsy.was.data.domain.User;
import comsy.was.data.dtomapper.UserMapper;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {

    @JsonProperty("guid")
    private Long guid;

    @JsonProperty("pm_level")
    private Short pmLevel;

    @JsonProperty("pm_name")
    private String pmName;


    public static UserDto from(User user){
        return UserMapper.INSTANCE.toDto(user);
    }

    public User toEntity(){
        return UserMapper.INSTANCE.toEntity(this);
    }


    public static List<UserDto> from(List<User> entityList){
        return UserMapper.INSTANCE.toDtoList(entityList);
    }

    public static List<User> toEntityList(List<UserDto> dtoList){
        return UserMapper.INSTANCE.toEntityList(dtoList);
    }
}