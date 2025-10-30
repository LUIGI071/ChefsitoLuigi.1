package es.luigi.chefsitoLuigi.Mapper;

import es.luigi.chefsitoLuigi.Dto.PantryItemDto;
import es.luigi.chefsitoLuigi.Entity.PantryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface PantryItemMapper {
    @Mapping(source = "user.id", target = "userId")
    PantryItemDto toDto(PantryItem p);

    // toEntity would require user lookup elsewhere; implement manually in service
}