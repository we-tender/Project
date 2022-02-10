package zemat.wetender.domain.liquor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.cocktail.Cocktail;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorFile {

    @Id
    @GeneratedValue
    @Column(name = "liquorFile_id")
    private Long id;

    private String uploadLiquorFileName;

    private String storeLiquorFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

    public void setLiquor(Liquor liquor) {
        this.liquor = liquor;
    }

    public LiquorFile(String uploadLiquorFileName, String storeLiquorFileName) {
        this.uploadLiquorFileName = uploadLiquorFileName;
        this.storeLiquorFileName = storeLiquorFileName;
    }
}
