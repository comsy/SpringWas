package comsy.was.dtomapper;

import comsy.was.domain.Character;
import comsy.was.dto.CharacterDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CharacterMapper extends GenericMapper<CharacterDto, Character>{
    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);
}
