package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import zemat.wetender.domain.cocktail.*;
import zemat.wetender.dto.AttachFileDto;
import zemat.wetender.dto.cocktailDto.CocktailIngredientDto;
import zemat.wetender.dto.cocktailDto.CocktailLiquorDto;
import zemat.wetender.dto.cocktailDto.CocktailSequenceDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class CocktailUpdateForm {

    @NotNull
    private Long id;

    @NotBlank(message = "공백 x")
    private String name;

    private String eName;

    @NotBlank
    private String base;

    @NotNull(message = "도수는 0~100도 사이여야합니다.")
    @Range(min = 0, max = 100)
    private int abv;

    private String oneLine;

    @NotBlank
    private String content;

    @NotNull(message = "이미지는 한장 이상 이어야합니다.")
    private List<AttachFileDto> attachList;

    private List<String> tastes;

    @NotNull(message = "순서는 한개 이상이어야합니다.")
    private List<CocktailSequenceDto> sequences;

    private List<CocktailLiquorDto> liquors;

    private List<CocktailIngredientDto> ingredients;

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

        this.tastes = cocktail.getCocktailTastes().stream().map(CocktailTaste::getCocktailTasteName).collect(Collectors.toList());
        this.sequences = cocktail.getCocktailSequences().stream().map( CocktailSequence::toDto ).collect( Collectors.toList() );
        this.liquors = cocktail.getCocktailLiquors().stream().map( CocktailLiquor::toDto ).collect( Collectors.toList() );
        this.ingredients = cocktail.getCocktailIngredients().stream().map( CocktailIngredient::toDto ).collect( Collectors.toList());
        this.attachList = cocktail.getCocktailFiles().stream().map( CocktailFile::toDto ).collect( Collectors.toList());
    }
}
