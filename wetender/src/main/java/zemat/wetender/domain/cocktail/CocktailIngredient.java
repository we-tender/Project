package zemat.wetender.domain.cocktail;

import lombok.*;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.cocktailDto.CocktailIngredientDto;
import zemat.wetender.dto.cocktailDto.CocktailSequenceDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CocktailIngredient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocktail_ingredient_id")
    private Long id;

    private String cocktailIngredientQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    Cocktail cocktail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    @Builder
    public CocktailIngredient(String cocktailIngredientQty, Ingredient ingredient) {
        this.cocktailIngredientQty = cocktailIngredientQty;
        if(ingredient != null){
            ingredient.addCocktailIngredient(this);
            this.ingredient = ingredient;
        }
    }

    public CocktailIngredientDto toDto(){
        return new CocktailIngredientDto(this);
    }

}
