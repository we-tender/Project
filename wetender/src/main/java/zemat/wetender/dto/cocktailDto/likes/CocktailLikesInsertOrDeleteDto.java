package zemat.wetender.dto.cocktailDto.likes;


import lombok.Data;

@Data
public class CocktailLikesInsertOrDeleteDto {

    private Long cocktailId;
    private String memberName;

}
