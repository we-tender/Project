package zemat.wetender.domain.liquor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LiquorFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    public LiquorFile(Long id, String uploadLiquorFileName, String storeLiquorFileName) {
        this.id = id;
        this.uploadLiquorFileName = uploadLiquorFileName;
        this.storeLiquorFileName = storeLiquorFileName;
    }
}
