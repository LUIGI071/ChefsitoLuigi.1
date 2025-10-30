package es.luigi.chefsitoLuigi.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_lists")
public class ShoppingList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ElementCollection
    @CollectionTable(name="shopping_entries", joinColumns = @JoinColumn(name="shopping_list_id"))
    @Column(name = "entry")
    private List<String> entries;
}