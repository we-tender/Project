package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cocktail extends BaseEntity {

    @Id
    @Column(name = "cocktail_id")
    @GeneratedValue
    private Long id;

    private String cocktailName;

    private String cocktailEName;

    private String cocktailBase;

    private int cocktailAbv;

    private String cocktailOneLine;

    private String cocktailContent;

    @OneToMany(mappedBy = "cocktail")
    private List<CocktailFile> cocktailImage;

    private String cocktailSeqeunce;

    @OneToMany(mappedBy = "cocktail")
    private List<CocktailIngredient> cocktailIngredients;

    @OneToMany(mappedBy = "cocktail")
    private List<CocktailTaste> cocktailTastes;

}