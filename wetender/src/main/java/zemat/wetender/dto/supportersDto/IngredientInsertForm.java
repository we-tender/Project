package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import zemat.wetender.dto.AttachFileDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class IngredientInsertForm {

    @NotBlank(message = "공백 x")
    private String name;

    private String eName;

    @NotBlank
    private String content;

    @NotNull(message = "이미지는 한장 이상 이어야합니다.")
    private List<AttachFileDto> attachList;

    public IngredientInsertForm() {

    }
}
