package comsy.was.data.dtomapper;

import comsy.was.data.domain.Character;
import comsy.was.data.dto.CharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterMapper extends GenericMapper<CharacterDto, Character>{
    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);
}
