package es.luigi.chefsitoLuigi.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PantryItemDto {
    private Long id;
    @NotNull(message = "El ID del usuario es obligatorio.")
    private Long userId;
    @NotNull(message = "El ID del ingrediente es obligatorio.")
    private Long ingredientId;
    @NotNull(message = "La cantidad no puede ser nula.")
    @Positive(message = "La cantidad debe ser mayor que 0.")
    private Double quantity;
}