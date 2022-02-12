package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.ingredient.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
