package es.luigi.chefsitoLuigi.Service;

import es.luigi.chefsitoLuigi.Dto.RecipeDto;

import java.util.List;

public interface RecommendationService {
    /**
     * Genera recomendaciones (recipes) para un usuario a partir de su despensa.
     * Retorna recetas ordenadas por % de coincidencia (implementación: comparar ingredientes).
     */
    List<RecipeDto> recommendForUser(Long userId, int limit);
}