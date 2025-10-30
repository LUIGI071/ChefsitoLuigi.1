package es.luigi.chefsitoLuigi.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCreateRequest {
    private String title;
    private String instructions;
    private Integer preparationMinutes;
    private String category;
    private List<Long> ingredientIds; // ids from ingredients table
    private Set<String> dietTags;
    private String difficulty;
}