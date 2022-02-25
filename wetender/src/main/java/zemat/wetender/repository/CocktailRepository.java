package zemat.wetender.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail,Long> {

    @Query(value = "select c from Cocktail c order by c.cocktailRecommendation desc")
    Page<Cocktail> findByCocktailRecommendation(Pageable pageable);
}
