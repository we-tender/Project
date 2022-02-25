package zemat.wetender.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query(countQuery = "select count(*) from Ingredient i")
    Page<Ingredient> findByIngredientNameContainingIgnoreCaseOrIngredientEnameContainingIgnoreCase(Pageable pageable, String ingredientName, String ingredientEname);
}
