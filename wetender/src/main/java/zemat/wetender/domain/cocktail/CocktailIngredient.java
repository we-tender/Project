package zemat.wetender.domain.cocktail;

import lombok.*;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.liquor.Liquor;

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
    @JoinColumn(name = "liquor_id")
    Liquor liquor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    @Builder
    public CocktailIngredient(String cocktailIngredientQty, Liquor liquor, Ingredient ingredient) {
        this.cocktailIngredientQty = cocktailIngredientQty;
        this.liquor = liquor;
        this.ingredient = ingredient;
        if(liquor != null){
            liquor.addCocktailIngredient(this);
        }
        if(ingredient != null){
            ingredient.addCocktailIngredient(this);
        }
    }


}
