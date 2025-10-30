package es.luigi.chefsitoLuigi.Service;

import es.luigi.chefsitoLuigi.Dto.ExternalRecipeDto;
import java.util.List;

public interface RecipeSuggestionService {

    // Buscar recetas según ingredientes escritos por el usuario
    List<ExternalRecipeDto> suggestRecipesByIngredients(List<String> ingredients);

    // Buscar recetas basadas en la despensa (pantry) del usuario
    List<ExternalRecipeDto> suggestRecipesForUser(Long userId);
}
