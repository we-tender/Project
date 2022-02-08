package zemat.wetender.domain.liquor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Liquor {

    @Id
    @GeneratedValue
    @Column(name = "Liquor_id")
    private Long id;

    private String liquorName;

    private String liquorEName;

    private int liquorAbv;

    private String liquorOneLine;

    private String liquorContent;

    @OneToMany(mappedBy = "liquor")
    private List<LiquorFile> liquorImage;


}
