package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import zemat.wetender.domain.cocktail.Cocktail;
import zemat.wetender.domain.cocktail.CocktailFile;
import zemat.wetender.domain.cocktail.CocktailTaste;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class LiquorUpdateForm {

    @NotNull
    private Long id;

    @NotBlank(message = "공백 x")
    private String name;

    private String eName;

    @NotNull(message = "도수는 0~100도 사이여야합니다.")
    @Range(min = 0, max = 100)
    private int abv;

    private String oneLine;

    @NotBlank
    private String content;

    @NotNull(message = "이미지는 한장 이상 이어야합니다.")
    private List<AttachFileDto> attachList;

    public LiquorUpdateForm() {
    }

    public LiquorUpdateForm(Liquor liquor) {
        this.id = liquor.getId();
        this.name = liquor.getLiquorName();
        this.eName = liquor.getLiquorEname();
        this.abv = liquor.getLiquorAbv();
        this.oneLine = liquor.getLiquorOneLine();
        this.content = liquor.getLiquorContent();
        this.attachList = liquor.getLiquorFiles().stream().map( LiquorFile:: toDto ).collect( Collectors.toList());
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
