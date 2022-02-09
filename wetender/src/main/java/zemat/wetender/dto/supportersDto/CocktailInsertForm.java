package zemat.wetender.dto.supportersDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class CocktailInsertForm {

    private Long id;

    private String name;

    private String eName;

    private String base;

    private int abv;

    private String oneLine;

    private String content;

//    private List<MultipartFile> image;

    private List<String> tastes;
}
