package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.member.Member;
import zemat.wetender.repository.IngredientRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional
    public Long insertIngredient(Ingredient ingredient) {
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return savedIngredient.getId();
    }

    public Page<Ingredient> pageFindKeyword(Pageable pageable, String keyword){

        return ingredientRepository.findByIngredientNameContainingIgnoreCaseOrIngredientEnameContainingIgnoreCase(pageable,keyword,keyword);
    }

    public Ingredient findById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).get();
    }


    @Transactional
    public Ingredient findByIngredientName(String ingredientName) {
        Ingredient ingredient = ingredientRepository.findByIngredientName(ingredientName).orElseThrow(() -> new IllegalStateException("존재하지 않는 재료입니다!"));
        ingredient.getCocktailIngredients().size();
        return ingredient;
    }

    @Transactional
    public void update(Long ingredientId, Ingredient updateIngredient) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        ingredient.update(updateIngredient);
    }

    @Transactional
    public void deleteById(Long ingredientId){
        ingredientRepository.deleteById(ingredientId);
    }
}
