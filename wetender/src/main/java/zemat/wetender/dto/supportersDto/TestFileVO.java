package zemat.wetender.dto.supportersDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TestFileVO {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private boolean fileType;
    private Long seq_bno;
}
