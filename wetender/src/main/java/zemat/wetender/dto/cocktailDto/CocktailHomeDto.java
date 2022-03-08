package zemat.wetender.dto.cocktailDto;

import lombok.Getter;
import zemat.wetender.domain.cocktail.Cocktail;

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

    public CocktailHomeDto(Cocktail cocktail) {
        this.id = cocktail.getId();
        this.name = cocktail.getCocktailName();
        this.ename = cocktail.getCocktailEname();
        this.base = cocktail.getCocktailBase();
        this.abv = cocktail.getCocktailAbv();
        this.oneLine = cocktail.getCocktailOneLine();
        this.recommendation = cocktail.getCocktailRecommendation();
        this.mainImage = cocktail.getCocktailFiles().get(0).getStoreCocktailFileName();
    }
}
