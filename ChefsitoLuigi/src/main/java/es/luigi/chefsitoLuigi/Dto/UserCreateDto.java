package es.luigi.chefsitoLuigi.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @Email @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String fullName;
}