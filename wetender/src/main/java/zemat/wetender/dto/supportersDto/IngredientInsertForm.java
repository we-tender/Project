package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class IngredientInsertForm {

    private Long id;

    @NotNull
    private String name;

    private String eName;

    @NotNull
    private String content;

    private List<MultipartFile> images;
}
