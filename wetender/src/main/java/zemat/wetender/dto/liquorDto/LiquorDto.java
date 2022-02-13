package zemat.wetender.dto.liquorDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;

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

        List<LiquorFile> liquorFiles = liquor.getLiquorFiles();
        for (LiquorFile liquorFile : liquorFiles) {
            addLiquorFileDto(liquorFile);
        }
    }

    public Liquor toEntity(){

        List<LiquorFile> liquorFiles = new ArrayList<>();
        for (LiquorFileDto image : images) {
            LiquorFile liquorFile = image.toEntity();
            liquorFiles.add(liquorFile);
        }

        return Liquor.builder()
                .id(id)
                .liquorOneLine(oneLine)
                .liquorAbv(abv)
                .liquorName(name)
                .liquorEname(eName)
                .liquorContent(content)
                .liquorFiles(liquorFiles)
                .build();

    }
}
