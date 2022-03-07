package zemat.wetender.dto.liquorDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.domain.liquor.LiquorFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LiquorPopDto {

    private Long id;

    private String name;

    private String eName;

    public LiquorPopDto(Long id, String name, String eName) {
        this.id = id;
        this.name = name;
        this.eName = eName;
    }
}
