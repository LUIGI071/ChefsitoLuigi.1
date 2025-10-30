package es.luigi.chefsitoLuigi.Service.Impl;

import es.luigi.chefsitoLuigi.Dto.*;
import es.luigi.chefsitoLuigi.Entity.*;
import es.luigi.chefsitoLuigi.Exception.ResourceNotFoundException;
import es.luigi.chefsitoLuigi.Mapper.RecipeMapper;
import es.luigi.chefsitoLuigi.Repository.*;
import es.luigi.chefsitoLuigi.Service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public RecipeDto create(RecipeCreateRequest req) {
        Recipe r = new Recipe();
        r.setTitle(req.getTitle());
        r.setInstructions(req.getInstructions());
        r.setPreparationMinutes(req.getPreparationMinutes());
        r.setCategory(req.getCategory());
        r.setDifficulty(req.getDifficulty());
        r.setDietTags(req.getDietTags() != null ? req.getDietTags() : Set.of());

        // lookup ingredients
        List<Ingredient> ingredients = req.getIngredientIds() == null ? List.of()
                : req.getIngredientIds().stream()
                .map(id -> ingredientRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id)))
                .collect(Collectors.toList());
        r.setIngredients(ingredients);

        Recipe saved = recipeRepository.save(r);
        return recipeMapper.toDto(saved);
    }

    @Override
    public RecipeDto update(Long id, RecipeCreateRequest req) {
        Recipe existing = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));
        existing.setTitle(req.getTitle());
        existing.setInstructions(req.getInstructions());
        existing.setPreparationMinutes(req.getPreparationMinutes());
        existing.setCategory(req.getCategory());
        existing.setDietTags(req.getDietTags() != null ? req.getDietTags() : Set.of());
        existing.setDifficulty(req.getDifficulty());

        List<Ingredient> ingredients = req.getIngredientIds() == null ? List.of()
                : req.getIngredientIds().stream()
                .map(iid -> ingredientRepository.findById(iid)
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", iid)))
                .collect(Collectors.toList());
        existing.setIngredients(ingredients);

        Recipe saved = recipeRepository.save(existing);
        return recipeMapper.toDto(saved);
    }

    @Override
    public RecipeDto findById(Long id) {
        Recipe r = recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));
        return recipeMapper.toDto(r);
    }

    @Override
    public List<RecipeDto> findAll() {
        return recipeRepository.findAll().stream().map(recipeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", id));
        recipeRepository.deleteById(id);
    }

    @Override
    public List<RecipeDto> findByDiet(String dietTag) {
        return recipeRepository.findAll().stream()
                .filter(r -> r.getDietTags() != null && r.getDietTags().contains(dietTag))
                .map(recipeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDto> findByMaxTime(int minutes) {
        return recipeRepository.findByPreparationMinutesLessThanEqual(minutes).stream()
                .map(recipeMapper::toDto).collect(Collectors.toList());
    }
}
