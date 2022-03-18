package zemat.wetender.dto.supportersDto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class CocktailInsertForm {

    @NotBlank(message = "공백 x")
    private String name;

    private String eName;

    @NotBlank
    private String base;

    @NotNull(message = "도수는 0~100도 사이여야합니다.")
    @Range(min = 0, max = 100)
    private int abv;

    private String oneLine;

    @NotBlank
    private String content;

    private List<AttachFileDto> attachList;

    private List<String> tastes = new ArrayList<>();

    private List<String> sequences = new ArrayList<>();

}
