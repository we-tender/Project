package zemat.wetender.dto.supportersDto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailLiquor;
import zemat.wetender.domain.cocktail.CocktailSequence;
import zemat.wetender.domain.cocktail.CocktailTaste;
import zemat.wetender.dto.AttachFileDto;
import zemat.wetender.dto.cocktailDto.CocktailIngredientDto;
import zemat.wetender.dto.cocktailDto.CocktailLiquorDto;
import zemat.wetender.dto.cocktailDto.CocktailSequenceDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CocktailInsertForm {

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

}
