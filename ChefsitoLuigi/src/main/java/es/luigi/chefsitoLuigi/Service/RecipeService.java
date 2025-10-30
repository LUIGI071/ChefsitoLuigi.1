package es.luigi.chefsitoLuigi.Service;
import es.luigi.chefsitoLuigi.Dto.RecipeDto;
import es.luigi.chefsitoLuigi.Dto.RecipeCreateRequest;
import java.util.List;

public interface RecipeService {
    RecipeDto create(RecipeCreateRequest req);
    RecipeDto update(Long id, RecipeCreateRequest req);
    RecipeDto findById(Long id);
    List<RecipeDto> findAll();
    void delete(Long id);
    List<RecipeDto> findByDiet(String dietTag);
    List<RecipeDto> findByMaxTime(int minutes);
}