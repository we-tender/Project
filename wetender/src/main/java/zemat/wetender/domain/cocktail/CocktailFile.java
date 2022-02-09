package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private String uploadFileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private Cocktail cocktail;

    public CocktailFile(String uploadFileName, String filePath) {
        this.uploadFileName = uploadFileName;
        this.filePath = filePath;
    }
}
