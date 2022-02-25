package zemat.wetender.dto.ingredientDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.ingredient.IngredientFile;

@Getter @Setter
public class IngredientFileDto {

    private Long id;

    private String uploadLiquorFileName;

    private String storeLiquorFileName;

    public IngredientFileDto(IngredientFile ingredientFile) {
        this.id = ingredientFile.getId();
        this.uploadLiquorFileName = ingredientFile.getUploadIngredientFileName();
        this.storeLiquorFileName = ingredientFile.getStoreIngredientFileName();
    }

    public IngredientFile toEntity(){
        return IngredientFile.builder()
                .id(id)
                .uploadIngredientFileName(uploadLiquorFileName)
                .storeIngredientFileName(storeLiquorFileName)
                .build();
    }
}
