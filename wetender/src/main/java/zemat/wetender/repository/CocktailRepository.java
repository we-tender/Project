package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail,Long> {

}
