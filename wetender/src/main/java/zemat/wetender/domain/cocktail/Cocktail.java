package zemat.wetender.domain.cocktail;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zemat.wetender.domain.base.BaseEntity;
import zemat.wetender.domain.base.BasePostEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cocktail extends BasePostEntity {

    @Id
    @Column(name = "cocktail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cocktailName;

    private String cocktailEname;

    private String cocktailBase;

    private int cocktailAbv;

    private String cocktailOneLine;

    private String cocktailContent;

    private int cocktailRecommendation;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CocktailFile> cocktailFiles = new ArrayList<>();

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CocktailSequence> cocktailSequences = new ArrayList<>();

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CocktailLiquor> cocktailLiquors = new ArrayList<>();

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CocktailTaste> cocktailTastes = new ArrayList<>();

    // 댓글
    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private List<CocktailReply> cocktailReplyList = new ArrayList<>();

    // 좋아요
    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private List<CocktailLikes> cocktailLikesList = new ArrayList<>();

    public void addCocktailIngredients(List<CocktailIngredient> addCocktailIngredients){
        for (CocktailIngredient cocktailIngredient : addCocktailIngredients) {
            cocktailIngredient.setCocktail(this);
            cocktailIngredients.add(cocktailIngredient);
        }
    }

    public void addCocktailLiquors(List<CocktailLiquor> addCocktailLiquors){
        for (CocktailLiquor cocktailLiquor : addCocktailLiquors) {
            cocktailLiquor.setCocktail(this);
            cocktailLiquors.add(cocktailLiquor);
        }
    }

    public void addCocktailTastes(List<CocktailTaste> addCocktailTastes){
        for (CocktailTaste cocktailTaste : addCocktailTastes) {
            cocktailTaste.setCocktail(this);
            cocktailTastes.add(cocktailTaste);
        }
    }

    public void addCocktailFiles(List<CocktailFile> addCocktailFiles){
        for (CocktailFile cocktailFile : addCocktailFiles) {
            cocktailFile.setCocktail(this);
            cocktailFiles.add(cocktailFile);
        }
    }

    public void addCocktailSequences(List<CocktailSequence> addCocktailSequences){
        for (CocktailSequence cocktailSequence : addCocktailSequences) {
            cocktailSequence.setCocktail(this);
            cocktailSequences.add(cocktailSequence);
        }
    }


    @Builder
    public Cocktail(String cocktailName, String cocktailEName, String cocktailBase, int cocktailAbv, String cocktailOneLine, String cocktailContent, int cocktailRecommendation,
                    List<CocktailTaste> cocktailTastes, List<CocktailFile> cocktailFiles, List<CocktailSequence> cocktailSequences, List<CocktailIngredient> cocktailIngredients,
                    List<CocktailLiquor> cocktailLiquors) {
        this.cocktailName = cocktailName;
        this.cocktailEname = cocktailEName;
        this.cocktailBase = cocktailBase;
        this.cocktailAbv = cocktailAbv;
        this.cocktailOneLine = cocktailOneLine;
        this.cocktailContent = cocktailContent;
        this.cocktailRecommendation = cocktailRecommendation;

        if(cocktailTastes != null){
            addCocktailTastes(cocktailTastes);
        }

        if(cocktailFiles != null){
            addCocktailFiles(cocktailFiles);
        }

        if(cocktailSequences != null){
            addCocktailSequences(cocktailSequences);
        }

        if(cocktailIngredients != null){
           addCocktailIngredients(cocktailIngredients);
        }

        if(cocktailLiquors != null){
            addCocktailLiquors(cocktailLiquors);
        }
    }

    public void update(Cocktail updateCocktail) {
        this.cocktailName = updateCocktail.getCocktailName();
        this.cocktailEname = updateCocktail.getCocktailEname();
        this.cocktailBase = updateCocktail.getCocktailBase();
        this.cocktailAbv = updateCocktail.getCocktailAbv();
        this.cocktailContent = updateCocktail.getCocktailContent();

        this.cocktailTastes.clear();
        addCocktailTastes(updateCocktail.getCocktailTastes());

        this.cocktailFiles.clear();
        addCocktailFiles(updateCocktail.getCocktailFiles());

        this.cocktailSequences.clear();
        addCocktailSequences(updateCocktail.getCocktailSequences());

        this.cocktailLiquors.clear();
        addCocktailLiquors(updateCocktail.getCocktailLiquors());

        this.cocktailIngredients.clear();
        addCocktailIngredients(updateCocktail.getCocktailIngredients());
    }
}