package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import zemat.wetender.domain.liquor.Liquor;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class LiquorInsertForm {

    @NotBlank(message = "공백 x")
    private String name;

    @NotBlank(message = "공백 x")
    private String eName;

    @NotNull(message = "도수는 0~100도 사이여야합니다.")
    @Range(min = 0, max = 100)
    private int abv;

    @NotBlank
    private String oneLine;

    @NotBlank
    private String content;

    @NotNull(message = "이미지는 한장 이상 이어야합니다.")
    private List<AttachFileDto> attachList;

    public LiquorInsertForm() {

    }
}
