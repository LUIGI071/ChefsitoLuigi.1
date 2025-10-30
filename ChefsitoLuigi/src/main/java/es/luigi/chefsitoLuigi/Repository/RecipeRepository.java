package es.luigi.chefsitoLuigi.Repository;

import es.luigi.chefsitoLuigi.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategoryIgnoreCase(String category);
    List<Recipe> findByPreparationMinutesLessThanEqual(Integer minutes);

    // ✅ DESCOMENTADO: Ahora que la entidad tiene externalId
    boolean existsByExternalId(String externalId);
    Optional<Recipe> findByExternalId(String externalId);

    Optional<Recipe> findByTitle(String title);

    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.id IN :ids GROUP BY r.id HAVING COUNT(DISTINCT i.id) = :size")
    List<Recipe> findRecipesWithAllIngredients(@Param("ids") List<Long> ids, @Param("size") long size);

    @Query(value = "SELECT recipe_id FROM recipe_ingredients WHERE ingredient_id IN :ids GROUP BY recipe_id ORDER BY COUNT(DISTINCT ingredient_id) DESC", nativeQuery = true)
    List<Long> findRecipeIdsOrderByMatches(@Param("ids") List<Long> ids);
}