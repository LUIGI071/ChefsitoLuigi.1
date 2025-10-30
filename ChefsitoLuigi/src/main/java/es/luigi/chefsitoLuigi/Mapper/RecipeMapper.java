package es.luigi.chefsitoLuigi.Mapper;
import es.luigi.chefsitoLuigi.Dto.RecipeDto;
import es.luigi.chefsitoLuigi.Entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface RecipeMapper {
    RecipeDto toDto(Recipe r);
    // toEntity is created in service (requires ingredient lookup)
}
