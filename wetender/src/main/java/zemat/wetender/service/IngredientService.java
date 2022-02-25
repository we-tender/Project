package zemat.wetender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.repository.IngredientRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Page<Ingredient> pageFindKeyword(Pageable pageable, String keyword){

        return ingredientRepository.findByIngredientNameContainingIgnoreCaseOrIngredientEnameContainingIgnoreCase(pageable,keyword,keyword);
    }

    public Ingredient findById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId).get();
    }
}
