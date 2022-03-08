package zemat.wetender.dto.liquorDto;

import lombok.Getter;
import zemat.wetender.domain.liquor.Liquor;

@Getter
public class LiquorHomeDto {
    private Long id;
    private String name;
    private String ename;
    private int abv;
    private String oneLine;
    private long recommendation;
    private String mainImage;

    public LiquorHomeDto(Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getLiquorName();
        this.ename = liquor.getLiquorEname();
        this.abv = liquor.getLiquorAbv();
        this.oneLine = liquor.getLiquorOneLine();
        this.recommendation = liquor.getLiquorRecommendation();
        this.mainImage = liquor.getLiquorFiles().get(0).getStoreLiquorFileName();
    }
}
