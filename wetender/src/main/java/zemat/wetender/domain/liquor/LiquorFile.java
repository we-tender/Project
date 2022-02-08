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
    @Column(name = "LiquorFile_id")
    private Long id;

    private String uploadFileName;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Liquor_id")
    private Liquor liquor;


}
