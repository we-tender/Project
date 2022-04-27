package zemat.wetender.dto.cocktailDto;

import lombok.Getter;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailFile;

@Getter
public class CocktailHomeDto {
    private Long id;
    private String name;
    private String ename;
    private String base;
    private int abv;
    private String oneLine;
    private int recommendation;
    private String mainImage;

    private Long views;
    private Long likes;
    private Long replies;

    public CocktailHomeDto(Cocktail cocktail) {
        this.id = cocktail.getId();
        this.name = cocktail.getCocktailName();
        this.ename = cocktail.getCocktailEname();
        this.base = cocktail.getCocktailBase();
        this.abv = cocktail.getCocktailAbv();
        this.oneLine = cocktail.getCocktailOneLine();
        this.recommendation = cocktail.getCocktailRecommendation();
        CocktailFile cocktailFile = cocktail.getCocktailFiles().get(0);
        this.mainImage = cocktailFile.getUploadPath() +"/" + cocktailFile.getUuid() + "_" + cocktailFile.getFileName();

        this.views = cocktail.getViews();
        this.likes = cocktail.getLikes();
        this.replies = cocktail.getReplies();

    }
}
