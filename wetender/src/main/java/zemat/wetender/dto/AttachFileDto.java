package zemat.wetender.dto;

import lombok.Data;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.ingredient.IngredientFile;
import zemat.wetender.domain.liquor.LiquorFile;

@Data
public class AttachFileDto {

    private Long id;
    private String fileName;
    private String uploadPath;
    private String uuid;
    private boolean fileType;

    public AttachFileDto() {
    }

    public AttachFileDto(String fileName, String uploadPath, String uuid, boolean fileType) {
        this.fileName = fileName;
        this.uploadPath = uploadPath;
        this.uuid = uuid;
        this.fileType = fileType;
    }

    public AttachFileDto(CocktailFile cocktailFile) {
        this.id = cocktailFile.getId();
        this.uuid = cocktailFile.getUuid();
        this.uploadPath = cocktailFile.getUploadPath();
        this.fileName = cocktailFile.getFileName();
        this.fileType = cocktailFile.isFileType();
    }

    public AttachFileDto(IngredientFile ingredientFile) {
        this.id = ingredientFile.getId();
        this.uuid = ingredientFile.getUuid();
        this.uploadPath = ingredientFile.getUploadPath();
        this.fileName = ingredientFile.getFileName();
        this.fileType = ingredientFile.isFileType();
    }

    public AttachFileDto(LiquorFile liquorFile) {
        this.id = liquorFile.getId();
        this.uuid = liquorFile.getUuid();
        this.uploadPath = liquorFile.getUploadPath();
        this.fileName = liquorFile.getFileName();
        this.fileType = liquorFile.isFileType();
    }

    public CocktailFile toCocktailFileEntity(){

        CocktailFile cocktailFile = CocktailFile.builder()
                .fileName(fileName)
                .fileType(fileType)
                .uploadPath(uploadPath)
                .uuid(uuid)
                .build();

        return cocktailFile;
    }

    public LiquorFile toLiquorFileEntity(){

        LiquorFile liquorFile = LiquorFile.builder()
                .fileName(fileName)
                .fileType(fileType)
                .uploadPath(uploadPath)
                .uuid(uuid)
                .build();

        return liquorFile;
    }

    public IngredientFile toIntgredientFileEntity(){
        return IngredientFile.builder()
                .fileName(fileName)
                .fileType(fileType)
                .uploadPath(uploadPath)
                .uuid(uuid)
                .build();
    }
}
