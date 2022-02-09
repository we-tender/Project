package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cocktail extends BaseEntity {

    @Id
    @Column(name = "cocktail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cocktailName;

    private String cocktailEname;

    private String cocktailBase;

    private int cocktailAbv;

    private String cocktailOneLine;

    private String cocktailContent;

//    @OneToMany(mappedBy = "cocktail")
//    private List<CocktailFile> cocktailImage;
//
//    private String cocktailSeqeunce;
//
//    @OneToMany(mappedBy = "cocktail")
//    private List<CocktailIngredient> cocktailIngredients;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private List<CocktailTaste> cocktailTastes = new ArrayList<>();

    public void addCocktailTaste(CocktailTaste cocktailTaste){
        cocktailTaste.setCocktail(this);
        cocktailTastes.add(cocktailTaste);
    }

    @Builder
    public Cocktail(String cocktailName, String cocktailEName, String cocktailBase, int cocktailAbv, String cocktailOneLine, String cocktailContent, List<CocktailTaste> cocktailTastes) {
        this.cocktailName = cocktailName;
        this.cocktailEname = cocktailEName;
        this.cocktailBase = cocktailBase;
        this.cocktailAbv = cocktailAbv;
        this.cocktailOneLine = cocktailOneLine;
        this.cocktailContent = cocktailContent;

        for (CocktailTaste cocktailTaste : cocktailTastes) {
            addCocktailTaste(cocktailTaste);
        }
    }


}