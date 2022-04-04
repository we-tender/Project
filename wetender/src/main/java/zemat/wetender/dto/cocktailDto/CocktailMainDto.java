package zemat.wetender.dto.cocktailDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.cocktail.CocktailLikes;
import zemat.wetender.domain.cocktail.CocktailReply;
import zemat.wetender.dto.AttachFileDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CocktailMainDto {

    private Long id;

    private String name;

    private String eName;

    private int abv;

    private String oneLine;

    private List<AttachFileDto> images = new ArrayList<>();

    // baseEntity
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    // 댓글
    private List<CocktailReply> cocktailReplyList = new ArrayList<>();

    // 좋아요
    private List<CocktailLikes> cocktailLikesList = new ArrayList<>();

    // basePostEntity
    private long views;
    private long likes;
    private long replies;

    public void addCocktailFileDto(CocktailFile cocktailFile){
        images.add(new AttachFileDto(cocktailFile));
    }

    public CocktailMainDto(Cocktail cocktail) {
        this.id = cocktail.getId();
        this.name = cocktail.getCocktailName();
        this.eName = cocktail.getCocktailEname();
        this.abv = cocktail.getCocktailAbv();
        this.oneLine = cocktail.getCocktailOneLine();

        List<CocktailFile> cocktailFiles = cocktail.getCocktailFiles();
        for (CocktailFile cocktailFile : cocktailFiles) {
            addCocktailFileDto(cocktailFile);
        }

        this.createdDate = cocktail.getCreatedDate();
        this.lastModifiedDate = cocktail.getLastModifiedDate();
        this.createdBy = cocktail.getCreatedBy();
        this.lastModifiedBy = cocktail.getLastModifiedBy();


        this.cocktailReplyList = cocktail.getCocktailReplyList();
        this.cocktailLikesList = cocktail.getCocktailLikesList();

        this.views = cocktail.getViews();
        this.likes = cocktail.getLikes();
        this.replies = cocktail.getReplies();


    }


}
