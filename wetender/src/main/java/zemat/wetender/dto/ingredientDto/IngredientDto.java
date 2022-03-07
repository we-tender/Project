package zemat.wetender.dto.ingredientDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.ingredient.Ingredient;
import zemat.wetender.domain.ingredient.IngredientFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class IngredientDto {


    private Long id;

    private String name;

    private String eName;

    private String content;

    private List<IngredientFileDto> images = new ArrayList<>();

    public void addIngredientFileDto(IngredientFile ingredientFile){
        images.add(new IngredientFileDto(ingredientFile));
    }

    public IngredientDto(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getIngredientName();
        this.eName = ingredient.getIngredientEname();
        this.content = ingredient.getIngredientContent();
        List<IngredientFile> ingredientFiles = ingredient.getIngredientFiles();
        for (IngredientFile ingredientFile : ingredientFiles) {
            addIngredientFileDto(ingredientFile);
        }
    }

    public Ingredient toEntity(){

        List<IngredientFile> ingredientFiles = new ArrayList<>();
        for (IngredientFileDto image : images) {
            IngredientFile ingredientFile = image.toEntity();
            ingredientFiles.add(ingredientFile);
        }

        return Ingredient.builder()
                .id(id)
                .ingredientName(name)
                .ingredientEname(eName)
                .ingredientContent(content)
                .ingredientFiles(ingredientFiles)
                .build();
    }
}
