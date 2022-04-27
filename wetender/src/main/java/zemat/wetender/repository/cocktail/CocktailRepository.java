package zemat.wetender.repository.cocktail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zemat.wetender.domain.cocktail.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail,Long> {

    @Query(value = "select c from Cocktail c join fetch c.cocktailFiles " +
            "order by c.cocktailRecommendation desc",
            countQuery = "select count(c) from Cocktail c")
    Page<Cocktail> findByCocktailRecommendation(Pageable pageable);



    Page<Cocktail> findAll(Pageable pageable);

    @Query(countQuery = "select count(*) from Cocktail m")
    Page<Cocktail> findByCocktailNameContainingIgnoreCaseOrCocktailEnameContainingIgnoreCase(Pageable pageable, String cocktailName, String cocktailEname);

}
