package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CocktailSequence extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cocktail_sequence_id")
    private Long id;

    private String cocktailSequenceContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }

    public CocktailSequence(String cocktailSequenceContent) {
        this.cocktailSequenceContent = cocktailSequenceContent;
    }
}
