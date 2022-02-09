package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.cocktail.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail,Long> {
}
