package zemat.wetender.dto.cocktailDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.CocktailLiquor;

@Getter @Setter
public class CocktailLiquorDto {

    private Long id; // liquorId

    private String name; // liquorName

    private String quantity;

    public CocktailLiquorDto(CocktailLiquor cocktailLiquor){
        this.id = cocktailLiquor.getLiquor().getId();
        this.name = cocktailLiquor.getLiquor().getLiquorName();
        this.quantity = cocktailLiquor.getCocktailIngredientQty();
    }

    public CocktailLiquorDto(){

    }
}
