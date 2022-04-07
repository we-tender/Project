package zemat.wetender.dto.cocktailDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.CocktailIngredient;

@Getter @Setter
public class CocktailIngredientDto {

    private Long id; //ingredientId

    private String name; //ingredientName

    private String quantity;

    public CocktailIngredientDto(CocktailIngredient cocktailIngredient){
        this.id = cocktailIngredient.getIngredient().getId();
        this.name = cocktailIngredient.getIngredient().getIngredientName();
        this.quantity = cocktailIngredient.getCocktailIngredientQty();
    }

    public CocktailIngredientDto(){

    }
}
