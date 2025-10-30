package es.luigi.chefsitoLuigi.Mapper;

import es.luigi.chefsitoLuigi.Dto.UserDto;
import es.luigi.chefsitoLuigi.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
