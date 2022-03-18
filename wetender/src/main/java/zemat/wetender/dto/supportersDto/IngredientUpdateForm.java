package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class IngredientUpdateForm {

    private Long id;

    @NotNull
    private String name;

    private String eName;

    @NotNull
    private String content;

    private List<AttachFileDto> attachList = new ArrayList<>();

    public IngredientUpdateForm() {
    }

    public IngredientUpdateForm(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getIngredientName();
        this.eName = ingredient.getIngredientEname();
        this.content = ingredient.getIngredientContent();
    }

    public Ingredient toEntity(){

        List<IngredientFile> ingredientFiles = new ArrayList<>();
        for (AttachFileDto ingredientFileDto : attachList) {
            ingredientFiles.add(ingredientFileDto.toIntgredientFileEntity());
        }

        return Ingredient.builder()
                .ingredientName(name)
                .ingredientContent(content)
                .ingredientEname(eName)
                .ingredientFiles(ingredientFiles)
                .build();

    }
}
