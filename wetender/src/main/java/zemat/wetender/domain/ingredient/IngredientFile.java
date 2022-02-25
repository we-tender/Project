package zemat.wetender.domain.ingredient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IngredientFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredientFile_id")
    private Long id;

    private String uploadIngredientFileName;

    private String storeIngredientFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }


    public IngredientFile(String uploadIngredientFileName, String storeIngredientFileName) {
        this.uploadIngredientFileName = uploadIngredientFileName;
        this.storeIngredientFileName = storeIngredientFileName;
    }

    @Builder
    public IngredientFile(Long id, String uploadIngredientFileName, String storeIngredientFileName) {
        this.id = id;
        this.uploadIngredientFileName = uploadIngredientFileName;
        this.storeIngredientFileName = storeIngredientFileName;
    }

}
