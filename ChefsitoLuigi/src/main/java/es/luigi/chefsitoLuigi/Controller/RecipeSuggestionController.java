package es.luigi.chefsitoLuigi.Controller;

import es.luigi.chefsitoLuigi.Dto.ExternalRecipeDto;
import es.luigi.chefsitoLuigi.Service.RecipeSuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suggestions")
@RequiredArgsConstructor
public class RecipeSuggestionController {

    private final RecipeSuggestionService recipeSuggestionService;

    @PostMapping("/by-ingredients")
    public ResponseEntity<List<ExternalRecipeDto>> suggestRecipesByIngredients(
            @RequestBody Map<String, List<String>> request
    ) {
        List<String> ingredients = request.get("ingredients");
        if (ingredients == null || ingredients.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        return ResponseEntity.ok(recipeSuggestionService.suggestRecipesByIngredients(ingredients));
    }

    @GetMapping("/for-user/{userId}")
    public ResponseEntity<List<ExternalRecipeDto>> getUserRecommendations(@PathVariable Long userId) {
        return ResponseEntity.ok(recipeSuggestionService.suggestRecipesForUser(userId));
    }
}