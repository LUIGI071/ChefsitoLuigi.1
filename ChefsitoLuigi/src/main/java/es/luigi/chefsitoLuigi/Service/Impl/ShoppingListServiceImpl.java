package es.luigi.chefsitoLuigi.Service.Impl;

import es.luigi.chefsitoLuigi.Dto.ShoppingListDto;
import es.luigi.chefsitoLuigi.Entity.*;
import es.luigi.chefsitoLuigi.Exception.ResourceNotFoundException;
import es.luigi.chefsitoLuigi.Repository.*;
import es.luigi.chefsitoLuigi.Service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public ShoppingListDto createFromRecipes(Long userId, List<Long> recipeIds) {
        User u = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Set<String> entries = new LinkedHashSet<>();
        for (Long rid : recipeIds) {
            Recipe r = recipeRepository.findById(rid).orElseThrow(() -> new ResourceNotFoundException("Recipe", "id", rid));
            r.getIngredients().forEach(i -> entries.add(i.getName()));
        }
        ShoppingList sl = ShoppingList.builder().user(u).entries(new ArrayList<>(entries)).build();
        ShoppingList saved = shoppingListRepository.save(sl);
        return ShoppingListDto.builder().id(saved.getId()).userId(u.getId()).entries(saved.getEntries()).build();
    }

    @Override
    public List<ShoppingListDto> listByUser(Long userId) {
        return shoppingListRepository.findByUserId(userId).stream()
                .map(s -> ShoppingListDto.builder().id(s.getId()).userId(s.getUser().getId()).entries(s.getEntries()).build())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        shoppingListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ShoppingList","id",id));
        shoppingListRepository.deleteById(id);
    }
}
