package es.luigi.chefsitoLuigi.Controller;

import es.luigi.chefsitoLuigi.Dto.PantryItemDto;
import es.luigi.chefsitoLuigi.Service.PantryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pantry")
@RequiredArgsConstructor
public class PantryController {

    private final PantryService pantryService;

    @Operation(summary = "Añadir item a la despensa")
    @PostMapping
    public ResponseEntity<PantryItemDto> addItem(@Valid @RequestBody PantryItemDto dto) {
        PantryItemDto created = pantryService.addItem(dto);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Listar items por usuario")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PantryItemDto>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(pantryService.listByUser(userId));
    }

    @Operation(summary = "Actualizar cantidad de item")
    @PutMapping("/{id}")
    public ResponseEntity<PantryItemDto> updateQuantity(@PathVariable Long id, @RequestParam Double quantity) {
        PantryItemDto dto = pantryService.updateQuantity(id, quantity);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Eliminar item de despensa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pantryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
