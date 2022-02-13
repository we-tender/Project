package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.dto.ingredientDto.LiquorIngredientDto;
import zemat.wetender.dto.liquorDto.LiquorDto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class CocktailInsertForm {

    private Long id;

    @NotNull
    private String name;

    private String eName;

    @NotNull
    @Range(min = 0, max = 100)
    private String base;

    @NotNull
    private int abv;

    private String oneLine;

    @NotNull
    private String content;

    private List<MultipartFile> images;

    private List<String> tastes;

}
