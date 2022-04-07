package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class IngredientUpdateForm {

    @NotNull
    private Long id;

    @NotBlank(message = "공백 x")
    private String name;

    private String eName;

    @NotBlank
    private String content;

    @NotNull(message = "이미지는 한장 이상 이어야합니다.")
    private List<AttachFileDto> attachList;

    public IngredientUpdateForm() {
    }

    public IngredientUpdateForm(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getIngredientName();
        this.eName = ingredient.getIngredientEname();
        this.content = ingredient.getIngredientContent();
        this.attachList = ingredient.getIngredientFiles().stream().map( IngredientFile::toDto ).collect( Collectors.toList());
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
