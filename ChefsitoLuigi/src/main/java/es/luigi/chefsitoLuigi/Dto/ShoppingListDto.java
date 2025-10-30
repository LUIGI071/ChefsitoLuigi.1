package es.luigi.chefsitoLuigi.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto {

    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio.")
    private Long userId;

    @NotEmpty(message = "La lista de compras no puede estar vacía.")
    private List<
            @NotBlank(message = "Los elementos de la lista no pueden estar vacíos.")
            @Size(max = 100, message = "Cada elemento debe tener menos de 100 caracteres.")
                    String
            > entries;
}