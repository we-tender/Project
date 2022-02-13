package zemat.wetender.dto.liquorDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.liquor.LiquorFile;

@Getter @Setter
public class LiquorFileDto {

    private Long id;

    private String uploadLiquorFileName;

    private String storeLiquorFileName;

    public LiquorFileDto(LiquorFile liquorFile) {
        this.id = liquorFile.getId();
        this.uploadLiquorFileName = liquorFile.getUploadLiquorFileName();
        this.storeLiquorFileName = liquorFile.getStoreLiquorFileName();
    }

    public LiquorFile toEntity(){
        return LiquorFile.builder()
                .id(id)
                .uploadLiquorFileName(uploadLiquorFileName)
                .storeLiquorFileName(storeLiquorFileName)
                .build();
    }
}
