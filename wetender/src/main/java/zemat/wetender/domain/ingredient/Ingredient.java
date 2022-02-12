package zemat.wetender.domain.ingredient;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.cocktail.CocktailIngredient;
import zemat.wetender.domain.liquor.LiquorFile;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    private String ingredientName;

    private String ingredientEname;

    private String ingredientContent;

    @OneToMany(mappedBy = "ingredient",cascade = CascadeType.ALL)
    private List<IngredientFile> ingredientFiles = new ArrayList<>();

    @OneToMany(mappedBy = "ingredient")
    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

    public void addCocktailIngredient(CocktailIngredient cocktailIngredient){
        cocktailIngredients.add(cocktailIngredient);
    }

    public void addIngredientFile(IngredientFile ingredientFile){
        ingredientFile.setIngredient(this);
        ingredientFiles.add(ingredientFile);
    }

    @Builder
    public Ingredient(Long id, String ingredientName, String ingredientEname, String ingredientContent, List<IngredientFile> ingredientFiles) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.ingredientEname = ingredientEname;
        this.ingredientContent = ingredientContent;

        for (IngredientFile ingredientFile : ingredientFiles) {
            addIngredientFile(ingredientFile);
        }
    }
}
