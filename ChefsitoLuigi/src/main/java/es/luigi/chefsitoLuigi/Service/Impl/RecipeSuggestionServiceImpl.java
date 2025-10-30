package es.luigi.chefsitoLuigi.Service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.luigi.chefsitoLuigi.Dto.ExternalRecipeDto;
import es.luigi.chefsitoLuigi.Dto.IngredientDto;
import es.luigi.chefsitoLuigi.Dto.PantryItemDto;
import es.luigi.chefsitoLuigi.Entity.Recipe;
import es.luigi.chefsitoLuigi.Repository.RecipeRepository;
import es.luigi.chefsitoLuigi.Service.IngredientService;
import es.luigi.chefsitoLuigi.Service.PantryService;
import es.luigi.chefsitoLuigi.Service.RecipeSuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeSuggestionServiceImpl implements RecipeSuggestionService {

    private final RecipeRepository recipeRepository;
    private final PantryService pantryService;
    private final IngredientService ingredientService;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String API_BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String FILTER_URL = API_BASE_URL + "filter.php?i=";
    private static final String LOOKUP_URL = API_BASE_URL + "lookup.php?i=";

    @Override
    public List<ExternalRecipeDto> suggestRecipesByIngredients(List<String> ingredients) {
        if (ingredients == null || ingredients.isEmpty()) {
            return Collections.emptyList();
        }

        String query = ingredients.get(0).trim().replace(" ", "%20");
        String url = FILTER_URL + query;

        try {
            String json = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(json).get("meals");

            if (root == null || !root.isArray()) {
                return Collections.emptyList();
            }

            List<ExternalRecipeDto> results = new ArrayList<>();

            for (JsonNode meal : root) {
                String externalId = meal.get("idMeal").asText();

                // ✅ NUEVO: Obtener detalles completos de la receta
                ExternalRecipeDto detailedRecipe = getRecipeDetails(externalId);
                results.add(detailedRecipe);
            }

            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // ✅ NUEVO MÉTODO: Obtener detalles completos de una receta
    private ExternalRecipeDto getRecipeDetails(String externalId) {
        try {
            String detailUrl = LOOKUP_URL + externalId;
            String detailJson = restTemplate.getForObject(detailUrl, String.class);
            JsonNode detailRoot = mapper.readTree(detailJson).get("meals");

            if (detailRoot != null && detailRoot.isArray() && detailRoot.size() > 0) {
                JsonNode detailedMeal = detailRoot.get(0);

                String name = getSafeText(detailedMeal, "strMeal");
                String thumb = getSafeText(detailedMeal, "strMealThumb");
                String instructions = getSafeText(detailedMeal, "strInstructions");
                String category = getSafeText(detailedMeal, "strCategory");

                // Extraer ingredientes (vienen como strIngredient1, strIngredient2, ...)
                List<String> ingredientList = extractIngredients(detailedMeal);

                ExternalRecipeDto dto = ExternalRecipeDto.builder()
                        .title(name)
                        .imageUrl(thumb)
                        .instructions(instructions)
                        .category(category)
                        .externalId(externalId)
                        .source("themealdb")
                        .build();

                // Guardar en BD con información completa
                recipeRepository.findByExternalId(externalId).orElseGet(() -> {
                    Recipe newRecipe = Recipe.builder()
                            .title(name)
                            .instructions(instructions)
                            .category(category)
                            .imageUrl(thumb)
                            .externalId(externalId)
                            .source("themealdb")
                            .build();
                    return recipeRepository.save(newRecipe);
                });

                return dto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fallback: información básica si falla el detalle
        return getBasicRecipeInfo(externalId);
    }

    // ✅ Método auxiliar: extraer ingredientes
    private List<String> extractIngredients(JsonNode meal) {
        List<String> ingredients = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String ingredient = getSafeText(meal, "strIngredient" + i);
            String measure = getSafeText(meal, "strMeasure" + i);

            if (ingredient != null && !ingredient.trim().isEmpty()) {
                String ingredientWithMeasure = ingredient +
                        (measure != null && !measure.trim().isEmpty() ? " (" + measure.trim() + ")" : "");
                ingredients.add(ingredientWithMeasure);
            }
        }
        return ingredients;
    }

    // ✅ Método auxiliar: obtener texto seguro
    private String getSafeText(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return (field != null && !field.isNull()) ? field.asText() : null;
    }

    // ✅ Método auxiliar: información básica (como antes)
    private ExternalRecipeDto getBasicRecipeInfo(String externalId) {
        // ... código similar al que ya tenías
        return ExternalRecipeDto.builder()
                .title("Receta " + externalId)
                .instructions("Consulta detalles en TheMealDB")
                .category("General")
                .externalId(externalId)
                .source("themealdb")
                .build();
    }

    @Override
    public List<ExternalRecipeDto> suggestRecipesForUser(Long userId) {
        try {
            // ✅ Obtener items de la despensa del usuario
            List<PantryItemDto> pantryItems = pantryService.listByUser(userId);

            if (pantryItems == null || pantryItems.isEmpty()) {
                return Collections.emptyList();
            }

            // ✅ Extraer nombres de los ingredientes del usuario
            List<String> ingredients = new ArrayList<>();
            for (PantryItemDto pantryItem : pantryItems) {
                try {
                    IngredientDto ingredientDto = ingredientService.findById(pantryItem.getIngredientId());
                    if (ingredientDto != null && ingredientDto.getName() != null) {
                        ingredients.add(ingredientDto.getName());
                    }
                } catch (Exception e) {
                    // Continuar con el siguiente ingrediente si hay error
                    continue;
                }
            }

            if (ingredients.isEmpty()) {
                return Collections.emptyList();
            }

            // ✅ Buscar recetas con el primer ingrediente
            return suggestRecipesByIngredients(Collections.singletonList(ingredients.get(0)));

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}