package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.repository.CocktailRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    public List<Cocktail> findTop20ByRecommendation() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Cocktail> page = cocktailRepository.findByCocktailRecommendation(pageRequest);

        return page.getContent();
    }
}
