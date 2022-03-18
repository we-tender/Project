package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class LiquorInsertForm {

    private Long id;

    @NotNull
    private String name;

    private String eName;

    @NotNull
    private int abv;

    private String oneLine;

    @NotNull
    private String content;

    private List<AttachFileDto> attachList;
}
