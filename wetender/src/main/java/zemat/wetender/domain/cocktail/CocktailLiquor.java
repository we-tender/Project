package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.liquor.Liquor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CocktailLiquor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocktail_liquor_id")
    private Long id;

    private String cocktailIngredientQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    Cocktail cocktail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    Liquor liquor;

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    @Builder
    public CocktailLiquor(String cocktailIngredientQty, Liquor liquor) {
        this.cocktailIngredientQty = cocktailIngredientQty;
        this.liquor = liquor;
        if(liquor != null){
            liquor.addCocktailLiquor(this);
        }
    }
}
