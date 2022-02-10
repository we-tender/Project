package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import zemat.wetender.domain.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CocktailFile extends BaseEntity {

    @Id
    @GeneratedValue

    @Column(name = "cocktailFile_id")
    private Long id;

    private String uploadCocktailFileName;

    private String storeCocktailFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;


    public CocktailFile(String uploadFileName, String storeFileName) {
        this.uploadCocktailFileName = uploadFileName;
        this.storeCocktailFileName = storeFileName;
    }

    public void setCocktail(Cocktail cocktail) {
        this.cocktail = cocktail;
    }
}