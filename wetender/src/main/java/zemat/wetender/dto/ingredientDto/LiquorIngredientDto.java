package zemat.wetender.dto.ingredientDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.CocktailIngredient;
import zemat.wetender.dto.liquorDto.LiquorDto;

@Getter @Setter
public class LiquorIngredientDto {

    private Long id;

    private LiquorDto liquorDto;

    private String qty;

    public CocktailIngredient toEntity(){
        return CocktailIngredient.builder()
                .liquor(liquorDto.toEntity())
                .cocktailIngredientQty(qty)
                .build();
    }


}
