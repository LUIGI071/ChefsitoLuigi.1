package es.luigi.chefsitoLuigi.Dto.Auth;
import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @Email @NotNull
    private String email;
    @NotNull @Size(min=4)
    private String password;
    private String fullName;
}
