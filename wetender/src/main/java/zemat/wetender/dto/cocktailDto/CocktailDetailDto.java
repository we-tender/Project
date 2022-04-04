package zemat.wetender.dto.cocktailDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.dto.AttachFileDto;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CocktailDetailDto {

    private Long id;

    private String name;

    private String eName;

    private int abv;

    private String oneLine;

    private String content;

    private List<AttachFileDto> images = new ArrayList<>();

    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

    private List<CocktailLiquor> cocktailLiquors = new ArrayList<>();

    private List<CocktailTaste> tastes = new ArrayList<>();

    private List<CocktailSequence> sequences = new ArrayList<>();

    // 댓글
    private List<CocktailReply> cocktailReplyList = new ArrayList<>();

    // 좋아요
    private List<CocktailLikes> cocktailLikesList = new ArrayList<>();

    // basePostEntity
    private long views;
    private long likes;
    private long replies;

    private void addCocktailFileDto(CocktailFile cocktailFile){ images.add(new AttachFileDto(cocktailFile));}

    public CocktailDetailDto(Cocktail cocktail) {
        this.id = cocktail.getId();
        this.name = cocktail.getCocktailName();
        this.eName = cocktail.getCocktailEname();
        this.abv = cocktail.getCocktailAbv();
        this.oneLine = cocktail.getCocktailOneLine();
        this.content = cocktail.getCocktailContent();
        this.cocktailIngredients = cocktail.getCocktailIngredients();
        this.cocktailLiquors = cocktail.getCocktailLiquors();
        this.tastes = cocktail.getCocktailTastes();
        this.sequences = cocktail.getCocktailSequences();

        List<CocktailFile> cocktailFiles = cocktail.getCocktailFiles();
        for (CocktailFile cocktailFile : cocktailFiles) {
            addCocktailFileDto(cocktailFile);
        }

        this.cocktailReplyList = cocktail.getCocktailReplyList();
        this.cocktailLikesList = cocktail.getCocktailLikesList();

        this.views = cocktail.getViews();
        this.likes = cocktail.getLikes();
        this.replies = cocktail.getReplies();

    }
}
