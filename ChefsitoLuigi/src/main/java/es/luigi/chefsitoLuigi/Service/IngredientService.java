package es.luigi.chefsitoLuigi.Service;
import es.luigi.chefsitoLuigi.Dto.IngredientDto;
import java.util.List;

public interface IngredientService {
    IngredientDto create(IngredientDto dto);
    IngredientDto update(Long id, IngredientDto dto);
    IngredientDto findById(Long id);
    List<IngredientDto> findAll();
    void delete(Long id);
}
