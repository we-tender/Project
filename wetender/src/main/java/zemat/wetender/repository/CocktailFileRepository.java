package zemat.wetender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.cocktail.CocktailFile;

public interface CocktailFileRepository extends JpaRepository<CocktailFile,Long> {
}
