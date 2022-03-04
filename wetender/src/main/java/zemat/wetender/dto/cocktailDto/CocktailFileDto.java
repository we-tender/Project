package zemat.wetender.dto.cocktailDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.CocktailFile;

@Getter @Setter
public class CocktailFileDto {

    private Long id;

    private String uploadCocktailFileName;

    private String storeCocktailFileName;

    public CocktailFileDto(CocktailFile cocktailFile) {
        this.id = cocktailFile.getId();
        this.uploadCocktailFileName = cocktailFile.getUploadCocktailFileName();
        this.storeCocktailFileName = cocktailFile.getStoreCocktailFileName();
    }

    public CocktailFile toEntity(){
        return CocktailFile.builder()
                .id(id)
                .uploadCocktailFileName(uploadCocktailFileName)
                .storeCocktailFileName(storeCocktailFileName)
                .build();
    }
}
