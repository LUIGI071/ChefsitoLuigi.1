package es.luigi.chefsitoLuigi.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {

    private Long id;

    @NotBlank(message = "El título de la receta es obligatorio.")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres.")
    private String title;

    @NotBlank(message = "Las instrucciones son obligatorias.")
    @Size(min = 10, max = 4000, message = "Las instrucciones deben tener al menos 10 caracteres.")
    private String instructions;

    @NotNull(message = "El tiempo de preparación es obligatorio.")
    @Min(value = 1, message = "El tiempo de preparación debe ser al menos 1 minuto.")
    private Integer preparationMinutes;

    @NotBlank(message = "La categoría es obligatoria (ej. desayuno, vegano, sin gluten).")
    private String category;

    @NotEmpty(message = "Debe incluir al menos un ingrediente.")
    private List<Long> ingredientIds;
}