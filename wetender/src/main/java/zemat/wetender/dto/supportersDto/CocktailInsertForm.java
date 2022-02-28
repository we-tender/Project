package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class CocktailInsertForm {

    private Long id;

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

//    @NotNull(message = "한장의 이미지는 있어야합니다!") 안먹는데 이유 아직 못찾음..
    private List<MultipartFile> images;

    private List<String> tastes;
}
