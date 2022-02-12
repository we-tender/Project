package zemat.wetender.domain.liquor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.cocktail.CocktailIngredient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Liquor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liquor_id")
    private Long id;

    private String liquorName;

    private String liquorEname;

    private int liquorAbv;

    private String liquorOneLine;

    private String liquorContent;

    @OneToMany(mappedBy = "liquor",cascade = CascadeType.ALL)
    private List<LiquorFile> liquorFiles = new ArrayList<>();

    @OneToMany(mappedBy = "liquor")
    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

    public void addCocktailIngredient(CocktailIngredient cocktailIngredient){
        cocktailIngredients.add(cocktailIngredient);
    }

    public void addLiquorFile(LiquorFile liquorFile){
        liquorFile.setLiquor(this);
        liquorFiles.add(liquorFile);
    }

    @Builder
    public Liquor(Long id, String liquorName, String liquorEname, int liquorAbv, String liquorOneLine, String liquorContent, List<LiquorFile> liquorFiles) {
        this.id = id;
        this.liquorName = liquorName;
        this.liquorEname = liquorEname;
        this.liquorAbv = liquorAbv;
        this.liquorOneLine = liquorOneLine;
        this.liquorContent = liquorContent;

        for (LiquorFile liquorFile : liquorFiles) {
            addLiquorFile(liquorFile);
        }
    }
}
