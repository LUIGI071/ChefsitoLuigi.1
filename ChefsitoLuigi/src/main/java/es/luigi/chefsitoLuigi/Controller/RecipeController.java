package es.luigi.chefsitoLuigi.Controller;

import es.luigi.chefsitoLuigi.Dto.*;
import es.luigi.chefsitoLuigi.Service.RecipeService;
import es.luigi.chefsitoLuigi.Service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecommendationService recommendationService;

    @Operation(summary = "Crear receta")
    @PostMapping
    public ResponseEntity<RecipeDto> create(@Valid @RequestBody RecipeCreateRequest req) {
        return ResponseEntity.status(201).body(recipeService.create(req));
    }

    @Operation(summary = "Listar recetas")
    @GetMapping
    public ResponseEntity<List<RecipeDto>> list() {
        return ResponseEntity.ok(recipeService.findAll());
    }

    @Operation(summary = "Filtrar por dieta")
    @GetMapping("/diet/{tag}")
    public ResponseEntity<List<RecipeDto>> byDiet(@PathVariable String tag) {
        return ResponseEntity.ok(recipeService.findByDiet(tag));
    }

    @Operation(summary = "Filtrar por tiempo máximo (minutos)")
    @GetMapping("/time")
    public ResponseEntity<List<RecipeDto>> byTime(@RequestParam int maxMinutes) {
        return ResponseEntity.ok(recipeService.findByMaxTime(maxMinutes));
    }

    @Operation(summary = "Recomendaciones para usuario")
    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<List<RecipeDto>> recommendations(@PathVariable Long userId, @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recommendationService.recommendForUser(userId, limit));
    }
}
