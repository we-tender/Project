package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.repository.CocktailRepository;
import zemat.wetender.repository.IngredientRepository;
import zemat.wetender.repository.LiquorRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SupportersService {

    private final CocktailRepository cocktailRepository;
    private final LiquorRepository liquorRepository;
    private final IngredientRepository ingredientRepository;

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

    @Transactional
    public long ingredientSave(Ingredient ingredient){
        Ingredient ingredient1 = ingredientRepository.save(ingredient);

        return ingredient1.getId();
    }

}
