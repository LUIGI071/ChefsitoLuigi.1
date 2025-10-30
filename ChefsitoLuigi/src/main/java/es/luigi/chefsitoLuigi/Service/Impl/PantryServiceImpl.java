package es.luigi.chefsitoLuigi.Service.Impl;

import es.luigi.chefsitoLuigi.Dto.PantryItemDto;
import es.luigi.chefsitoLuigi.Entity.*;
import es.luigi.chefsitoLuigi.Exception.ResourceNotFoundException;
import es.luigi.chefsitoLuigi.Mapper.IngredientMapper;
import es.luigi.chefsitoLuigi.Repository.*;
import es.luigi.chefsitoLuigi.Service.PantryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PantryServiceImpl implements PantryService {

    private final PantryItemRepository pantryItemRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public PantryItemDto addItem(PantryItemDto dto) {
        // validar existencia user e ingredient
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
        Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", "id", dto.getIngredientId()));

        PantryItem item = PantryItem.builder()
                .user(user)
                .ingredient(ingredient)
                .quantity(dto.getQuantity())
                .build();

        PantryItem saved = pantryItemRepository.save(item);

        return PantryItemDto.builder()
                .id(saved.getId())
                .userId(user.getId())
                .ingredientId(ingredient.getId())
                .quantity(saved.getQuantity())
                .build();
    }

    @Override
    public List<PantryItemDto> listByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return pantryItemRepository.findByUser(user).stream()
                .map(p -> PantryItemDto.builder()
                        .id(p.getId())
                        .userId(user.getId())
                        .ingredientId(p.getIngredient().getId())
                        .quantity(p.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public PantryItemDto updateQuantity(Long id, Double quantity) {
        PantryItem item = pantryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PantryItem", "id", id));
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity debe ser mayor que 0");
        }
        item.setQuantity(quantity);
        PantryItem saved = pantryItemRepository.save(item);
        return PantryItemDto.builder()
                .id(saved.getId())
                .userId(saved.getUser().getId())
                .ingredientId(saved.getIngredient().getId())
                .quantity(saved.getQuantity())
                .build();
    }

    @Override
    public void delete(Long id) {
        PantryItem item = pantryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PantryItem", "id", id));
        pantryItemRepository.delete(item);
    }
}
