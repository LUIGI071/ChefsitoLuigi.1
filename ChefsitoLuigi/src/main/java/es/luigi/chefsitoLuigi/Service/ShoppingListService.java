package es.luigi.chefsitoLuigi.Service;

import es.luigi.chefsitoLuigi.Dto.ShoppingListDto;
import java.util.List;

public interface ShoppingListService {
    ShoppingListDto createFromRecipes(Long userId, List<Long> recipeIds);
    List<ShoppingListDto> listByUser(Long userId);
    void delete(Long id);
}
