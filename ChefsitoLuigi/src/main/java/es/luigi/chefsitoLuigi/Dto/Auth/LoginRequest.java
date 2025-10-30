package es.luigi.chefsitoLuigi.Dto.Auth;
import lombok.*;
import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email @NotNull
    private String email;
    @NotNull
    private String password;
}
