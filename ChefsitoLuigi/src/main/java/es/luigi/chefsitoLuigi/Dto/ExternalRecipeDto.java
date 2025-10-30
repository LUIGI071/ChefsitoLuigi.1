package es.luigi.chefsitoLuigi.Dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalRecipeDto {
    private Long id;
    private String title;
    private String instructions;
    private Integer preparationMinutes;
    private String category;
    private String imageUrl;
    private String externalId;
    private String source;
}