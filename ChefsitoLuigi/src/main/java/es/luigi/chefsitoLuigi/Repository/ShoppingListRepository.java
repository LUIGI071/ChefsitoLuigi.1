package es.luigi.chefsitoLuigi.Repository;

import es.luigi.chefsitoLuigi.Entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findByUserId(Long userId);
}