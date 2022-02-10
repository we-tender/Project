package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.repository.CocktailRepository;
import zemat.wetender.repository.LiquorRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SupportersService {

    private final CocktailRepository cocktailRepository;

    private final LiquorRepository liquorRepository;

    @Transactional
    public long cocktailSave(Cocktail cocktail){
        Cocktail cocktail1 = cocktailRepository.save(cocktail);

        return cocktail1.getId();
    }

    @Transactional
    public long liquorSave(Liquor liquor){
        Liquor liquor1 = liquorRepository.save(liquor);

        return liquor1.getId();
    }

}
