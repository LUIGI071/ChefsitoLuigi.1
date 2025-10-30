package es.luigi.chefsitoLuigi.Service.Impl;

import es.luigi.chefsitoLuigi.Dto.IngredientDto;
import es.luigi.chefsitoLuigi.Entity.Ingredient;
import es.luigi.chefsitoLuigi.Exception.ResourceNotFoundException;
import es.luigi.chefsitoLuigi.Mapper.IngredientMapper;
import es.luigi.chefsitoLuigi.Repository.IngredientRepository;
import es.luigi.chefsitoLuigi.Service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientDto create(IngredientDto dto) {
        Ingredient entity = ingredientMapper.toEntity(dto);
        Ingredient saved = ingredientRepository.save(entity);
        return ingredientMapper.toDto(saved);
    }

    @Override
    public IngredientDto update(Long id, IngredientDto dto) {
        Ingredient existing = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id));
        existing.setName(dto.getName());
        existing.setQuantity(dto.getQuantity());
        existing.setUnit(dto.getUnit());
        existing.setImageUrl(dto.getImageUrl());
        existing.setExpiryDate(dto.getExpiryDate());
        Ingredient saved = ingredientRepository.save(existing);
        return ingredientMapper.toDto(saved);
    }

    @Override
    public IngredientDto findById(Long id) {
        Ingredient e = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id));
        return ingredientMapper.toDto(e);
    }

    @Override
    public List<IngredientDto> findAll() {
        return ingredientRepository.findAll().stream().map(ingredientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        ingredientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", id));
        ingredientRepository.deleteById(id);
    }
}
