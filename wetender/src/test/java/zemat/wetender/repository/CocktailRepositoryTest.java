package zemat.wetender.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.repository.cocktail.CocktailRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Transactional
@SpringBootTest
class CocktailRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired
    CocktailRepository cocktailRepository;

    @Test
    void 칵테일_상위20추천수_조회() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Cocktail> page = cocktailRepository.findByCocktailRecommendation(pageRequest);

        List<Cocktail> content = page.getContent();
        for (Cocktail cocktail : content) {
            System.out.println("cocktail = " + cocktail);
        }
    }
}