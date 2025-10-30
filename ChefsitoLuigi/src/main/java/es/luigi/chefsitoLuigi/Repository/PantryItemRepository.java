package es.luigi.chefsitoLuigi.Repository;

import es.luigi.chefsitoLuigi.Entity.PantryItem;
import es.luigi.chefsitoLuigi.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {
    List<PantryItem> findByUser(User user);
    List<PantryItem> findByUserId(Long userId);
}