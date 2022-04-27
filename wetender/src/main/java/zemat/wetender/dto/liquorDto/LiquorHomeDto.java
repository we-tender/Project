package zemat.wetender.dto.liquorDto;

import lombok.Getter;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;

@Getter
public class LiquorHomeDto {
    private Long id;
    private String name;
    private String ename;
    private int abv;
    private String oneLine;
    private long recommendation;
    private String mainImage;

    private long views;
    private long likes;
    private long replies;



    public LiquorHomeDto(Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getLiquorName();
        this.ename = liquor.getLiquorEname();
        this.abv = liquor.getLiquorAbv();
        this.oneLine = liquor.getLiquorOneLine();
        this.recommendation = liquor.getLiquorRecommendation();

        LiquorFile liquorFile = liquor.getLiquorFiles().get(0);
        this.mainImage = liquorFile.getUploadPath() +"/" + liquorFile.getUuid() + "_" + liquorFile.getFileName();

        this.views = liquor.getViews();
        this.likes = liquor.getLikes();
        this.replies = liquor.getReplies();
    }
}
