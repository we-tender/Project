package zemat.wetender.dto.liquorDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zemat.wetender.domain.cocktail.CocktailIngredient;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
public class LiquorDto {

    private Long id;

    private String name;

    private String eName;

    private int abv;

    private String oneLine;

    private String content;

    private List<LiquorFileDto> images = new ArrayList<>();

    private List<CocktailIngredient> cocktails = new ArrayList<>();

    // baseEntity
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String createdBy;
    private String lastModifiedBy;

    public void addLiquorFileDto(LiquorFile liquorFile){
        images.add(new LiquorFileDto(liquorFile));
    }

    public LiquorDto(Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getLiquorName();
        this.eName = liquor.getLiquorEname();
        this.abv = liquor.getLiquorAbv();
        this.oneLine = liquor.getLiquorOneLine();
        this.content = liquor.getLiquorContent();
        this.cocktails = liquor.getCocktailIngredients();

        List<LiquorFile> liquorFiles = liquor.getLiquorFiles();
        for (LiquorFile liquorFile : liquorFiles) {
            addLiquorFileDto(liquorFile);
        }

        this.createdDate = liquor.getCreatedDate();
        this.lastModifiedDate = liquor.getLastModifiedDate();
        this.createdBy = liquor.getCreatedBy();
        this.lastModifiedBy = liquor.getLastModifiedBy();
    }

    //현재 미사용
//    public Liquor toEntity(){
//
//        List<LiquorFile> liquorFiles = new ArrayList<>();
//        for (LiquorFileDto image : images) {
//            LiquorFile liquorFile = image.toEntity();
//            liquorFiles.add(liquorFile);
//        }
//
//        return Liquor.builder()
//                .id(id)
//                .liquorOneLine(oneLine)
//                .liquorAbv(abv)
//                .liquorName(name)
//                .liquorEname(eName)
//                .liquorContent(content)
//                .liquorFiles(liquorFiles)
//                .build();
//
//    }
}
