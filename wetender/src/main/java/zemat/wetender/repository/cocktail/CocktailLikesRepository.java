package zemat.wetender.repository.cocktail;


import org.springframework.data.jpa.repository.JpaRepository;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailLikes;
import zemat.wetender.domain.member.Member;

import java.util.Optional;

public interface CocktailLikesRepository extends JpaRepository<CocktailLikes, Long> {

    Optional<CocktailLikes> findByMemberAndCocktail(Member member, Cocktail cocktail);

}
