package es.luigi.chefsitoLuigi.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    private Long id;

    @NotBlank(message = "El nombre del ingrediente es obligatorio.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String name;

    @NotBlank(message = "La unidad de medida es obligatoria (g, ml, unidades, etc.).")
    @Size(max = 20, message = "La unidad no puede tener más de 20 caracteres.")
    private String unit;

    @NotNull(message = "La cantidad no puede ser nula.")
    @Positive(message = "La cantidad debe ser mayor que 0.")
    private Double quantity;

    @Pattern(
            regexp = "^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|gif|png|jpeg)?$",
            message = "La URL de la imagen debe ser válida y terminar en .jpg, .png o .gif"
    )
    private String imageUrl;

    @FutureOrPresent(message = "La fecha de caducidad no puede ser pasada.")
    private LocalDate expiryDate;
}