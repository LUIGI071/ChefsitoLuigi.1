package es.luigi.chefsitoLuigi.Service.Impl;

import es.luigi.chefsitoLuigi.Dto.RecipeDto;
import es.luigi.chefsitoLuigi.Entity.*;
import es.luigi.chefsitoLuigi.Mapper.RecipeMapper;
import es.luigi.chefsitoLuigi.Repository.*;
import es.luigi.chefsitoLuigi.Service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecipeRepository recipeRepository;
    private final PantryItemRepository pantryItemRepository;
    private final UserRepository userRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public List<RecipeDto> recommendForUser(Long userId, int limit) {
        User user = userRepository.findById(userId).orElseThrow();
        List<PantryItem> pantry = pantryItemRepository.findByUser(user);
        Set<String> pantryIngredientNames = pantry.stream().map(p -> p.getIngredient().getName().toLowerCase()).collect(Collectors.toSet());

        // compute match percentage for each recipe
        var scored = recipeRepository.findAll().stream().map(recipe -> {
                    List<Ingredient> recIngs = recipe.getIngredients() == null ? List.of() : recipe.getIngredients();
                    int total = recIngs.size() == 0 ? 1 : recIngs.size();
                    long matched = recIngs.stream().filter(i -> pantryIngredientNames.contains(i.getName().toLowerCase())).count();
                    double percent = ((double) matched / total) * 100.0;
                    List<String> missing = recIngs.stream().filter(i -> !pantryIngredientNames.contains(i.getName().toLowerCase())).map(Ingredient::getName).collect(Collectors.toList());
                    return Map.<Object,Object>of("recipe", recipe, "percent", percent, "missing", missing);
                }).sorted((a,b) -> Double.compare((double) b.get("percent"), (double) a.get("percent")))
                .limit(limit)
                .collect(Collectors.toList());

        // Convert to RecipeDto and attach extra info if needed (we return only RecipeDto list here)
        return scored.stream().map(m -> recipeMapper.toDto((Recipe) m.get("recipe"))).collect(Collectors.toList());
    }
}
