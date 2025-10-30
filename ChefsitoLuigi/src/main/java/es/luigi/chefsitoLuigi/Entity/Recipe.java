package es.luigi.chefsitoLuigi.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 4000)
    private String instructions;

    private Integer preparationMinutes;

    private String category;

    @ManyToMany
    @JoinTable(name="recipe_ingredients",
            joinColumns = @JoinColumn(name="recipe_id"),
            inverseJoinColumns = @JoinColumn(name="ingredient_id"))
    private List<Ingredient> ingredients;

    @ElementCollection
    @CollectionTable(name = "recipe_diets", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "diet")
    private Set<String> dietTags;

    private String difficulty;

    // ✅ NUEVO: Campos para integración con APIs externas
    @Column(unique = true)
    private String externalId; // ID de la API externa (TheMealDB, etc.)
    private String imageUrl;   // URL de la imagen de la receta
    private String source;     // Fuente: 'local', 'themealdb', etc.
}