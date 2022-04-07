package zemat.wetender.dto.cocktailDto;

import lombok.Data;
import zemat.wetender.domain.cocktail.CocktailSequence;

@Data
public class CocktailSequenceDto {

    private Long id;

    private String content;

    public CocktailSequenceDto() {
    }

    public CocktailSequenceDto(CocktailSequence cocktailSequence) {
        this.id = cocktailSequence.getId();
        this.content = cocktailSequence.getCocktailSequenceContent();
    }


    public CocktailSequence toEntity(){

        CocktailSequence cocktailSequence = CocktailSequence.builder()
                .cocktailSequenceContent(content)
                .build();

        return cocktailSequence;
    }
}
