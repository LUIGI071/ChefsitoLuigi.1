package es.luigi.chefsitoLuigi.Controller;

import es.luigi.chefsitoLuigi.Dto.IngredientDto;
import es.luigi.chefsitoLuigi.Service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import es.luigi.chefsitoLuigi.Service.ImageService;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final ImageService imageService;

    @Operation(summary = "Crear ingrediente")
    @PostMapping
    public ResponseEntity<IngredientDto> create(@Valid @RequestBody IngredientDto dto) {
        IngredientDto created = ingredientService.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "Subir imagen y devolver URL")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = imageService.store(file);
        return ResponseEntity.ok(java.util.Map.of("url", url));
    }

    @Operation(summary = "Obtener ingrediente por id")
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

    @Operation(summary = "Listar ingredientes")
    @GetMapping
    public ResponseEntity<List<IngredientDto>> getAll() {
        return ResponseEntity.ok(ingredientService.findAll());
    }

    @Operation(summary = "Actualizar ingrediente")
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> update(@PathVariable Long id, @Valid @RequestBody IngredientDto dto) {
        return ResponseEntity.ok(ingredientService.update(id, dto));
    }

    @Operation(summary = "Eliminar ingrediente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
