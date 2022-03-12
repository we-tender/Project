package zemat.wetender.domain.liquor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.cocktail.CocktailIngredient;
import zemat.wetender.domain.cocktail.CocktailLiquor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Liquor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liquor_id")
    private Long id;

    private String liquorName;

    private String liquorEname;

    private int liquorAbv;

    private String liquorOneLine;

    private String liquorContent;

    private Long liquorView;

    private Long liquorRecommendation;

    @OneToMany(mappedBy = "liquor",cascade = CascadeType.ALL)
    private List<LiquorFile> liquorFiles = new ArrayList<>();


    @OneToMany(mappedBy = "liquor")
    private List<CocktailLiquor> cocktailLiquors = new ArrayList<>();

    public void addCocktailLiquor(CocktailLiquor cocktailLiquor){
        cocktailLiquors.add(cocktailLiquor);
    }

    public void addLiquorFile(LiquorFile liquorFile){
        liquorFile.setLiquor(this);
        liquorFiles.add(liquorFile);
    }

    @Builder
    public Liquor(Long id, String liquorName, String liquorEname, int liquorAbv, String liquorOneLine, String liquorContent, Long liquorView, Long liquorRecommendation,  List<LiquorFile> liquorFiles) {
        this.id = id;
        this.liquorName = liquorName;
        this.liquorEname = liquorEname;
        this.liquorAbv = liquorAbv;
        this.liquorOneLine = liquorOneLine;
        this.liquorContent = liquorContent;
        this.liquorView = liquorView;
        this.liquorRecommendation = liquorRecommendation;

        for (LiquorFile liquorFile : liquorFiles) {
            addLiquorFile(liquorFile);
        }
    }
}
