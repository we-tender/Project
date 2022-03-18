package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.cocktail.CocktailTaste;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class LiquorUpdateForm {

    private Long id;

    @NotNull
    private String name;

    private String eName;

    @NotNull
    private int abv;

    private String oneLine;

    @NotNull
    private String content;

    private List<AttachFileDto> attachList = new ArrayList<>();

    public LiquorUpdateForm() {
    }

    public LiquorUpdateForm(Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getLiquorName();
        this.eName = liquor.getLiquorEname();
        this.abv = liquor.getLiquorAbv();
        this.oneLine = liquor.getLiquorOneLine();
        this.content = liquor.getLiquorContent();
    }

    public Liquor toEntity(){

        List<LiquorFile> liquorFiles = new ArrayList<>();
        for (AttachFileDto liquorFileDto : attachList) {
            liquorFiles.add(liquorFileDto.toLiquorFileEntity());
        }

        return Liquor.builder()
                .liquorName(name)
                .liquorContent(content)
                .liquorAbv(abv)
                .liquorEname(eName)
                .liquorOneLine(oneLine)
                .liquorFiles(liquorFiles)
                .build();

    }
}
