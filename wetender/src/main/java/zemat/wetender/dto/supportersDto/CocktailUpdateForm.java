package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CocktailUpdateForm {

    private Long id;

    @NotBlank(message = "공백 x")
    private String name;

    private String eName;

    @NotBlank
    private String base;

    @NotNull(message = "도수는 0~100도 사이여야합니다.")
    @Range(min = 0, max = 100)
    private int abv;

    @NotBlank
    private String oneLine;

    @NotBlank
    private String content;

    private List<AttachFileDto> attachList = new ArrayList<>();

    private List<String> tastes = new ArrayList<>();

    private List<String> sequences = new ArrayList<>();

    private List<List<String>> liquors = new ArrayList<>();

    private List<List<String>> ingredients = new ArrayList<>();

    public void addLiquors(List<CocktailLiquor> liquors){
        for (CocktailLiquor liquor : liquors) {
            List<String> liquorContent = new ArrayList<>();
            liquorContent.add(liquor.getLiquor().getId() + "");
            liquorContent.add(liquor.getLiquor().getLiquorName());
            liquorContent.add(liquor.getCocktailIngredientQty());
            this.liquors.add(liquorContent);
        }
    }

    public void addIngredients(List<CocktailIngredient> ingredients){
        for (CocktailIngredient ingredient : ingredients) {
            List<String> ingredientContent = new ArrayList<>();
            ingredientContent.add(ingredient.getIngredient().getId() + "");
            ingredientContent.add(ingredient.getIngredient().getIngredientName());
            ingredientContent.add(ingredient.getCocktailIngredientQty());
            this.ingredients.add(ingredientContent);
        }
    }

    public void addTastes(List<CocktailTaste> tastes){
        for (CocktailTaste taste : tastes) {
            this.tastes.add(taste.getCocktailTasteName());
        }
    }

    public void addSequences(List<CocktailSequence> sequences){
        for (CocktailSequence sequence : sequences) {
            this.sequences.add(sequence.getCocktailSequenceContent());
        }
    }

    public CocktailUpdateForm() {
    }

    public CocktailUpdateForm(Cocktail cocktail) {
        this.id = cocktail.getId();
        this.name = cocktail.getCocktailName();
        this.eName = cocktail.getCocktailEname();
        this.base = cocktail.getCocktailBase();
        this.abv = cocktail.getCocktailAbv();
        this.oneLine = cocktail.getCocktailOneLine();
        this.content = cocktail.getCocktailContent();
        addTastes(cocktail.getCocktailTastes());
        addSequences(cocktail.getCocktailSequences());
        addLiquors(cocktail.getCocktailLiquors());
        addIngredients(cocktail.getCocktailIngredients());
    }


    public Cocktail toEntity(){

        List<CocktailTaste> cocktailTastes = new ArrayList<>();
        for (String taste : tastes) {
            cocktailTastes.add(new CocktailTaste(taste));
        }
        List<CocktailFile> cocktailFiles = new ArrayList<>();
        for (AttachFileDto cocktailFileDto : attachList) {
            cocktailFiles.add(cocktailFileDto.toCocktailFileEntity());
        }

        return Cocktail.builder()
                .cocktailName(name)
                .cocktailContent(content)
                .cocktailAbv(abv)
                .cocktailBase(base)
                .cocktailEName(eName)
                .cocktailOneLine(oneLine)
                .cocktailFiles(cocktailFiles)
                .cocktailTastes(cocktailTastes)
                .build();

    }
}
