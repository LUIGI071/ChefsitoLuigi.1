package es.luigi.chefsitoLuigi.Service;
import es.luigi.chefsitoLuigi.Dto.PantryItemDto;
import java.util.List;

public interface PantryService {
    PantryItemDto addItem(PantryItemDto dto);
    List<PantryItemDto> listByUser(Long userId);
    PantryItemDto updateQuantity(Long id, Double quantity);
    void delete(Long id);
}
