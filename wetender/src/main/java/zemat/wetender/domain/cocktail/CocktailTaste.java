package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CocktailTaste extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocktailTaste_id")
    private Long id;

    private String cocktailTasteName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }


    public CocktailTaste(String cocktailTasteName) {
        this.cocktailTasteName = cocktailTasteName;
    }
}
