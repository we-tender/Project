package zemat.wetender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.repository.CocktailRepository;

@Service
public class SupportersService {

    @Autowired
    CocktailRepository cocktailRepository;

    public long cocktailSave(Cocktail cocktail){
        Cocktail cocktail1 = cocktailRepository.save(cocktail);

        return cocktail1.getId();
    }
}
